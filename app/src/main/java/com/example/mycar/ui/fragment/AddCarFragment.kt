package com.example.mycar.ui.fragment

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mycar.BaseApplication
import com.example.mycar.R
import com.example.mycar.databinding.FragmentAddCarBinding
import com.example.mycar.model.MyCar
import com.example.mycar.ui.viewmodel.CarViewModel
import com.example.mycar.ui.viewmodel.CarViewModelFactory

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

    private var _binding: FragmentAddCarBinding? = null

    private val binding get() = _binding!!
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
        viewModel.brandAcquisition()
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
            binding.saveBtn.setOnClickListener {
                addCar()
            }
            binding.brandCarInput.setOnClickListener {
                setBrandCar()
            }

            binding.nameCarInput.setOnClickListener {
                setModelCar()
            }
        }
    }

    private fun addCar() {
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
    }


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
    }

    //to review
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
            findNavController().navigate(
                R.id.action_addCarFragment_to_carListFragment
            )
        }
    }

    private fun isValidEntry(): Boolean {
        return viewModel.isValidEntry(
            binding.nameCarInput.text.toString(),
            binding.brandCarInput.text.toString(),
            binding.doorsCarInput.text.toString(),
            binding.powerCarInput.text.toString(),
            binding.fuelCarInput.text.toString(),
            binding.yearCarInput.text.toString(),
            binding.placesCarInput.text.toString(),
            binding.colorCarInput.text.toString(),
            binding.kmCarInput.text.toString()
        )
    }

    //can serve
    /*private fun deleteCar(car: MyCar) {
        viewModel.deleteCar(car)
        findNavController().navigate(
            R.id.action_addCarFragment_to_carListFragment
        )
    }*/

    private fun noSecondFuel(car: MyCar): Boolean {
        if (car.secondFuel?.isBlank() == true) {
            return false
        }
        return true
    }

    private fun bindCar(car: MyCar) {
        binding.apply {
            nameCarInput.setText(car.name, TextView.BufferType.SPANNABLE)
            brandCarInput.setText(car.brand, TextView.BufferType.SPANNABLE)
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
            binding.brandCarInput.setText(items[viewModel.checkedItemBrand].toString())
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
            binding.nameCarInput.setText(itemsCar[viewModel.checkedItemModel].toString())
        }
        builderModel.setNegativeButton(R.string.cancel) { _: DialogInterface, _ ->
            binding.nameCarInput.setText("")
        }
        builderModel.show()
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