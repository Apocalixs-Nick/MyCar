package com.example.mycar.ui.fragment

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mycar.BaseApplication
import com.example.mycar.R
import com.example.mycar.databinding.FragmentCarDetailBinding
import com.example.mycar.model.MyCar
import com.example.mycar.network.logo.MyCarLogo
import com.example.mycar.utils.setAndGetUriByBrandParsingListOfLogoAndImageView
import com.example.mycar.ui.viewmodel.CarViewModel
import com.example.mycar.ui.viewmodel.CarViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * A simple [Fragment] subclass.
 * Use the [CarDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class CarDetailFragment : Fragment() {

    private val navigation: CarDetailFragmentArgs by navArgs()

    private val viewModel: CarViewModel by activityViewModels {
        CarViewModelFactory(
            (activity?.application as BaseApplication).database.myCarDao()
        )
    }


    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.rotate_open_anim
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.rotate_close_anim
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.from_bottom_animation
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.to_bottom_animation
        )
    }
    private var clicked = false

    private lateinit var car: MyCar

    private var _binding: FragmentCarDetailBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        try {
            // Inflate the layout for this fragment
            _binding = FragmentCarDetailBinding.inflate(inflater, container, false)
            return binding.root

        } catch (e: Exception) {
            Log.e("ErrorDetails", "onCreateViewCarDetailsFragment", e)
            throw e
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigation.id

        viewModel.retrieveCar(id).observe(this.viewLifecycleOwner) { selectCar ->
            car = selectCar
            bindCar()

        }

        binding.detailCar.setOnClickListener {
            addOnButtonClicked()
        }
    }

    private fun addOnButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.editCar.visibility = View.VISIBLE
            binding.shareCar.visibility = View.VISIBLE
            binding.deleteCar.visibility = View.VISIBLE
        } else {
            binding.editCar.visibility = View.GONE
            binding.shareCar.visibility = View.GONE
            binding.deleteCar.visibility = View.GONE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding.editCar.startAnimation(fromBottom)
            binding.shareCar.startAnimation(fromBottom)
            binding.deleteCar.startAnimation(fromBottom)
            binding.detailCar.startAnimation(rotateOpen)
        } else {
            binding.editCar.startAnimation(toBottom)
            binding.shareCar.startAnimation(toBottom)
            binding.deleteCar.startAnimation(toBottom)
            binding.detailCar.startAnimation(rotateClose)
        }
    }

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
    private fun bindCar() {
        binding.apply {
            nameCar.text = car.name
            brandCar.text = car.brand
            powerCar.text = car.power.toString() + " kW"
            doorCar.text = car.numberDoors.toString()
            fuelCar.text = car.fuel
            if (noSecondFuel(car)) {
                binding.icSecondFuelCar.visibility = View.VISIBLE
                binding.secondFuelCar.visibility = View.VISIBLE
            }
            secondFuelCar.text = car.secondFuel.toString()
            yearCar.text = car.productionYear.toString()
            placesCar.text = car.places.toString()
            colorCar.text = car.color
            kmCar.text = car.kM.toString() + " kM"
            deleteCar.setOnClickListener { showConfirmationDialog() }
            editCar.setOnClickListener { editCar() }
            if (imageCar != null) {
                imageCar.setImageBitmap(
                    BitmapFactory.decodeByteArray(
                        car.image, 0, car.image.size
                    )
                )
            } else {
                imageCar.setImageResource(R.drawable.ic_car)
            }
            shareCar.setOnClickListener {
                shareCar()
            }
            val observer = Observer<List<MyCarLogo>> {
                setAndGetUriByBrandParsingListOfLogoAndImageView(
                    viewModel.logoDataApi.value,
                    car.brand,
                    icBrandCar
                )
            }
            viewModel.logoDataApi.observe(viewLifecycleOwner, observer)
        }
    }

    /**
     * Private function for sharing a vehicle
     */
    private fun shareCar() {
        if (noSecondFuel(car)) {
            context?.let {
                shareCreation(
                    it,
                    getString(R.string.share_car),
                    getString(R.string.car) + "\nBrand: ${car.brand}\nModel: ${car.name}\nYear: ${car.productionYear}\nkM: ${car.kM} kM\nFuel: ${car.fuel}\nSecond fuel: ${car.secondFuel}"
                )
            }
        } else {
            context?.let {
                shareCreation(
                    it,
                    getString(R.string.share_car),
                    getString(R.string.car) + "\nBrand: ${car.brand}\nModel: ${car.name}\nYear: ${car.productionYear}\nkM: ${car.kM} kM\nFuel: ${car.fuel}"
                )
            }
        }
    }

    /**
     * Private function for creating the share
     */
    private fun shareCreation(context: Context, subject: String, text: String) {
        val type = "text/plain"

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = type
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, text)

        ContextCompat.startActivity(
            context,
            Intent.createChooser(intent, getString(R.string.share)),
            null
        )
    }

    /**
     * Private function for pop up display to confirm the deletion of a vehicle
     */
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteCar()
            }
            .show()
    }

    /**
     * Private function for delete a vehicle
     */
    private fun deleteCar() {
        viewModel.deleteCar(car)
        findNavController().navigateUp()
    }

    /**
     * Private function for edit a vehicle
     */
    private fun editCar() {
        val action = CarDetailFragmentDirections.actionCarDetailFragmentToAddCarFragment(
            car.id,
            getString(R.string.edit_fragment_title)
        )
        this.findNavController().navigate(action)
    }
}