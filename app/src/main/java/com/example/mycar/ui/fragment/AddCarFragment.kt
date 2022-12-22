package com.example.mycar.ui.fragment

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mycar.BaseApplication
import com.example.mycar.R
import com.example.mycar.databinding.FragmentAddCarBinding
import com.example.mycar.model.MyCar
import com.example.mycar.ui.viewmodel.CarNotificationViewModel
import com.example.mycar.ui.viewmodel.CarNotificationViewModelFactory
import com.example.mycar.ui.viewmodel.CarViewModel
import com.example.mycar.ui.viewmodel.CarViewModelFactory
import com.example.mycar.utils.checkForInternet
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 * Use the [AddCarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class AddCarFragment : Fragment() {

    private val navigation: AddCarFragmentArgs by navArgs()

    private lateinit var car: MyCar

    private val viewModel: CarViewModel by activityViewModels {
        CarViewModelFactory(
            (activity?.application as BaseApplication).database.myCarDao()
        )
    }

    private val viewModelNotification: CarNotificationViewModel by viewModels {
        CarNotificationViewModelFactory(requireActivity().application)
    }

    private var _binding: FragmentAddCarBinding? = null

    private val binding get() = _binding!!

    private val error = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        try {
            // Inflate the layout for this fragment
            _binding = FragmentAddCarBinding.inflate(inflater, container, false)
            return binding.root

        } catch (e: Exception) {
            Log.e("ErrorAdd", "onCreateViewAddCarFragment", e)
            throw e
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { swapConstraintIfInternet(it) }
        viewModel.brandAcquisition()
        viewModel.fuelAcquisition()
        binding.imgBtn.setOnClickListener {
            imageChooser()
        }
        val id = navigation.id
        if (id > 0) {
            viewModel.retrieveCar(id).observe(this.viewLifecycleOwner) { selectCar ->
                car = selectCar
                bindCar(car)
            }

            //can serve
            /*binding.deleteBtn.visibility = View.VISIBLE
            binding.deleteBtn.setOnClickListener {
                deleteCar(car)
            }*/
        } else {
            binding.nameCarLabel.isEnabled = false
            binding.nameCarInput.isEnabled = false

            controlBrandAdd()

            binding.saveBtn.setOnClickListener {
                addCar()
            }
            binding.brandCarInput.setOnClickListener {
                setBrandCar()
            }
            binding.nameCarInput.setOnClickListener {
                setModelCar()
            }
            binding.fuelCarInput.setOnClickListener {
                setFuelCar()
            }
        }
    }


    private fun controlBrandAdd() {
        /**
         * With addTextChangedListener you are going to add a listener to text that you are going to edit,
         * and you can apply this listener before, after, or on the edited text
         */
        binding.brandCarInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Unused
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // Unused
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // If the brandCarInput field is not empty, enable the nameCarInput field ì, otherwise disable
                if (s.toString().isNotBlank()) {
                    binding.nameCarLabel.isEnabled = true
                    binding.nameCarInput.isEnabled = true
                    binding.nameCarInput.setText("")
                } else {
                    binding.nameCarLabel.isEnabled = false
                    binding.nameCarInput.isEnabled = false
                    binding.nameCarInput.setText("")
                }
            }
        })
    }

    private fun controlBrandUpdate() {
        /**
         * With addTextChangedListener you are going to add a listener to text that you are going to edit,
         * and you can apply this listener before, after, or on the edited text
         */
        binding.brandCarInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Unused
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // Unused
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // If the brandCarInput field is not empty, enable the nameCarInput field ì, otherwise disable
                if (s.toString().isNotBlank()) {
                    binding.nameCarLabel.isEnabled = true
                    binding.nameCarInput.isEnabled = true
                    if (binding.brandCarInput.text.toString() != car.brand) {
                        binding.nameCarInput.setText("")
                    } else {
                        binding.nameCarInput.setText(binding.nameCarInput.text.toString())
                    }
                } else {
                    binding.nameCarLabel.isEnabled = false
                    binding.nameCarInput.isEnabled = false
                    binding.nameCarInput.setText("")
                }
            }
        })
    }


    /**
     * Private function for adding a new car
     */
    private fun addCar() {
        if (isValidEntry()) {
            if (isValidEntry() || noSecondFuel(car)) {
                calledViewModelAdd()
                findNavController().navigate(
                    R.id.action_addCarFragment_to_carListFragment
                )
                binding.saveBtn.setOnClickListener {

                }
            } else {
                calledViewModelAdd()
                findNavController().navigate(
                    R.id.action_addCarFragment_to_carListFragment
                )
            }
        } else {
            view?.let { Snackbar.make(it, snackBarBlank(), Snackbar.LENGTH_SHORT).show() }
        }

    }

    /**
     * Private function for the watching the field blank
     */
    private fun snackBarBlank(): Int {
        return when (error.isBlank()) {
            binding.brandCarInput.text.toString().isBlank() -> R.string.blank_brand
            binding.nameCarInput.text.toString().isBlank() -> R.string.blank_model
            binding.powerCarInput.text.toString().isBlank() -> R.string.blank_power
            binding.doorsCarInput.text.toString().isBlank() -> R.string.blank_door
            binding.yearCarInput.text.toString().isBlank() -> R.string.blank_year
            binding.placesCarInput.text.toString().isBlank() -> R.string.blank_places
            binding.colorCarInput.text.toString().isBlank() -> R.string.blank_color
            binding.kmCarInput.text.toString().isBlank() -> R.string.blank_km
            (binding.powerCarInput.text.toString().toInt() < 1 || binding.powerCarInput.text.toString().toInt() > 9999) -> R.string.error_power
            (binding.doorsCarInput.text.toString().toInt() < 1 || binding.doorsCarInput.text.toString().toInt() > 9) -> R.string.error_door
            (binding.yearCarInput.text.toString().toInt() < 1800 || binding.yearCarInput.text.toString().toInt() > 2050) -> R.string.error_year
            (binding.placesCarInput.text.toString().toInt() < 1 || binding.placesCarInput.text.toString().toInt() > 9) -> R.string.error_seat
            else -> R.string.error_snackbar
        }
    }

    /**
     * Private function for calling the addCar() function of the viewModel
     */
    private fun calledViewModelAdd() {
        viewModel.addCar(
            name = binding.nameCarInput.text.toString(),
            brand = binding.brandCarInput.text.toString(),
            power = binding.powerCarInput.text.toString(),
            numberDoors = binding.doorsCarInput.text.toString(),
            fuel = binding.fuelCarInput.text.toString(),
            secondFuel = binding.secondFuelCarInput.text.toString(),
            productionYear = binding.yearCarInput.text.toString(),
            image = createBitmapFromView(binding.previewImage),
            places = binding.placesCarInput.text.toString(),
            color = binding.colorCarInput.text.toString(),
            km = binding.kmCarInput.text.toString()
        )

        viewModelNotification.scheduleReminder(
            5,
            TimeUnit.SECONDS,
            binding.nameCarInput.text.toString(),
            binding.kmCarInput.text.toString().toInt()
        )
    }

    /**
     * Private function for editing data from an existing machine in the DB
     */
    private fun updateCar() {
        if (isValidEntry()) {
            viewModel.updateCar(
                id = navigation.id,
                name = binding.nameCarInput.text.toString(),
                brand = binding.brandCarInput.text.toString(),
                power = binding.powerCarInput.text.toString(),
                numberDoors = binding.doorsCarInput.text.toString(),
                fuel = binding.fuelCarInput.text.toString(),
                secondFuel = binding.secondFuelCarInput.text.toString(),
                image = createBitmapFromView(binding.previewImage),
                productionYear = binding.yearCarInput.text.toString(),
                places = binding.placesCarInput.text.toString(),
                color = binding.colorCarInput.text.toString(),
                km = binding.kmCarInput.text.toString()
            )

            viewModelNotification.scheduleReminder(
                5,
                TimeUnit.SECONDS,
                binding.nameCarInput.text.toString(),
                binding.kmCarInput.text.toString().toInt()
            )
            findNavController().navigate(
                R.id.action_addCarFragment_to_carListFragment
            )
        } else {
            view?.let { Snackbar.make(it, snackBarBlank(), Snackbar.LENGTH_SHORT).show() }
        }
    }

    /**
     * Private function for checking fields that are not blank
     */
    private fun isValidEntry(): Boolean {
        return viewModel.isValidEntry(
            name = binding.nameCarInput.text.toString(),
            brand = binding.brandCarInput.text.toString(),
            numberDoors = binding.doorsCarInput.text.toString(),
            power = binding.powerCarInput.text.toString(),
            fuel = binding.fuelCarInput.text.toString(),
            productionYear = binding.yearCarInput.text.toString(),
            places = binding.placesCarInput.text.toString(),
            color = binding.colorCarInput.text.toString(),
            km = binding.kmCarInput.text.toString()
        )
    }

    //can serve
    /*private fun deleteCar(car: MyCar) {
        viewModel.deleteCar(car)
        findNavController().navigate(
            R.id.action_addCarFragment_to_carListFragment
        )
    }*/

    /**
     * Private function to control the second fuel other than the primary fuel
     */
    private fun noSecondFuel(car: MyCar): Boolean {
        if (car.secondFuel?.isBlank() == true) {
            return false
        }
        return true
    }

    /**
     * Private function for setting fields
     */
    private fun bindCar(car: MyCar) {
        controlBrandUpdate()
        binding.apply {
            brandCarInput.setText(car.brand, TextView.BufferType.SPANNABLE)
            nameCarInput.setText(car.name, TextView.BufferType.SPANNABLE)
            doorsCarInput.setText(car.numberDoors.toString(), TextView.BufferType.SPANNABLE)
            fuelCarInput.setText(car.fuel, TextView.BufferType.SPANNABLE)
            if (noSecondFuel(car)) {
                secondFuelCarInput.setText(car.secondFuel, TextView.BufferType.SPANNABLE)
            } else {
                secondFuelCarInput.setText(null, TextView.BufferType.SPANNABLE)
            }

            if (previewImage != null) {
                previewImage.setImageBitmap(
                    BitmapFactory.decodeByteArray(
                        car.image, 0, car.image.size
                    )
                )
            } else {
                previewImage.setImageResource(R.drawable.ic_car)
            }

            powerCarInput.setText(car.power.toString(), TextView.BufferType.SPANNABLE)
            yearCarInput.setText(car.productionYear.toString(), TextView.BufferType.SPANNABLE)
            placesCarInput.setText(car.places.toString(), TextView.BufferType.SPANNABLE)
            colorCarInput.setText(car.color, TextView.BufferType.SPANNABLE)
            saveBtn.setOnClickListener {
                updateCar()
            }
            binding.brandCarInput.setOnClickListener {
                setBrandCar()
            }
            binding.nameCarInput.setOnClickListener {
                setModelCar()
            }
            binding.fuelCarInput.setOnClickListener {
                setFuelCar()
            }
            binding.kmCarInput.setText(car.kM.toString(), TextView.BufferType.SPANNABLE)
        }
    }

    /**
     * Private function for the appearance of an AlertDialog to select the car brand via API
     */
    private fun setBrandCar() {
        val listBrandCar = viewModel.getBrand()
        val items = arrayOfNulls<CharSequence>(listBrandCar.size)
        for (i in listBrandCar.indices) {
            items[i] = listBrandCar[i]
        }
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.choose_car)
        builder.setSingleChoiceItems(
            items,
            viewModel.checkedItemBrand
        ) { _: DialogInterface, which ->
            viewModel.checkedItemBrand = which
        }
        builder.setItems(items) { _: DialogInterface, which ->
            viewModel.checkedItemBrand = which
        }
        builder.setPositiveButton(R.string.Ok) { _: DialogInterface, _ ->
            if (viewModel.checkedItemBrand != -1) {
                binding.brandCarInput.setText(items[viewModel.checkedItemBrand].toString())
            } else {
                binding.brandCarInput.setText("")
                view?.let { Snackbar.make(it, R.string.no_brand, Snackbar.LENGTH_SHORT).show() }
            }
        }
        builder.setNegativeButton(R.string.cancel) { _: DialogInterface, _ ->
            binding.brandCarInput.setText("")
        }
        builder.show()
    }

    /**
     * Private function for the appearance of an AlertDialog to select the model of the selected brand via API
     */
    private fun setModelCar() {
        val listModelCar = viewModel.getModel(binding.brandCarInput.text.toString())
        val itemsCar = arrayOfNulls<CharSequence>(listModelCar.size)
        for (i in listModelCar.indices) {
            itemsCar[i] = listModelCar[i]
        }
        val builderModel: AlertDialog.Builder = AlertDialog.Builder(context)
        builderModel.setTitle(R.string.search_car)
        builderModel.setSingleChoiceItems(
            itemsCar,
            viewModel.checkedItemModel
        ) { _: DialogInterface, which ->
            viewModel.checkedItemModel = which
        }
        builderModel.setItems(itemsCar) { _: DialogInterface, which ->
            viewModel.checkedItemModel = which
        }
        builderModel.setPositiveButton(R.string.Ok) { _: DialogInterface, _ ->
            if (viewModel.checkedItemModel != -1) {
                binding.nameCarInput.setText(itemsCar[viewModel.checkedItemModel].toString())
            } else {
                binding.nameCarInput.setText("")
                view?.let { Snackbar.make(it, R.string.no_model, Snackbar.LENGTH_SHORT).show() }
            }
        }
        builderModel.setNegativeButton(R.string.cancel) { _: DialogInterface, _ ->
            binding.nameCarInput.setText("")
        }
        builderModel.show()
    }

    /**
     * Private function for the appearance of an AlertDialog to select the fuel
     */
    private fun setFuelCar() {
        val listFuelCar = viewModel.getFuel()
        val itemsFuel = arrayOfNulls<CharSequence>(listFuelCar.size)
        for (i in listFuelCar.indices) {
            itemsFuel[i] = listFuelCar[i]
        }
        val builderFuel: AlertDialog.Builder = AlertDialog.Builder(context)
        builderFuel.setTitle(R.string.type_fuel)
        builderFuel.setSingleChoiceItems(
            itemsFuel,
            viewModel.checkedItemFuel
        ) { _: DialogInterface, which ->
            viewModel.checkedItemFuel = which
        }
        builderFuel.setItems(itemsFuel) { _: DialogInterface, which ->
            viewModel.checkedItemFuel = which
        }
        builderFuel.setPositiveButton(R.string.Ok) { _: DialogInterface, _ ->
            if (viewModel.checkedItemFuel != -1) {
                binding.fuelCarInput.setText(itemsFuel[viewModel.checkedItemFuel].toString())
            } else {
                binding.fuelCarInput.setText("")
                view?.let { Snackbar.make(it, R.string.no_fuel, Snackbar.LENGTH_SHORT).show() }
            }
        }
        builderFuel.setNegativeButton(R.string.cancel) { _: DialogInterface, _ ->
            binding.fuelCarInput.setText("")
        }
        builderFuel.show()
    }

    /**
     * Private function to make the content of the add fragment visible or not if there is a connection or not
     */
    private fun swapConstraintIfInternet(context: Context) {
        if (checkForInternet(context)) {
            viewModel.refreshDataFromNetwork()
            binding.layoutAddNewCarWithConnection.visibility = View.VISIBLE
            binding.layoutAddNewCarWithoutConnection.visibility = View.GONE
        } else {
            binding.layoutAddNewCarWithConnection.visibility = View.GONE
            binding.layoutAddNewCarWithoutConnection.visibility = View.VISIBLE
            binding.retryAgainErrorConnection.setOnClickListener {
                swapConstraintIfInternet(context)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }


    // this function is triggered when
    // the Select Image Button is clicked
    private fun imageChooser() {
        // create an instance of the
        // intent of the type image
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), 200)
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == 200) {
                // Get the url of the image from data
                val selectedImageUri = data?.data
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    //preview
                    binding.previewImage.setImageURI(selectedImageUri)
                }
            }
        }
    }

    //Returns the bitmap
    private fun createBitmapFromView(view: View): Bitmap {
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        return view.drawingCache
    }
}