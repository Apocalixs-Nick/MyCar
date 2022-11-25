package com.example.mycar.ui.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mycar.BaseApplication
import com.example.mycar.R
import com.example.mycar.databinding.FragmentAddCarBinding
import com.example.mycar.databinding.FragmentCarDetailBinding
import com.example.mycar.model.MyCar
import com.example.mycar.ui.adapter.CarListAdapter
import com.example.mycar.ui.fragment.CarDetailFragmentDirections.Companion.actionCarDetailFragmentToAddCarFragment
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
    }

    private fun noSecondFuel(car: MyCar):Boolean {
        if (car.secondFuel?.isBlank() == true) {
            return false
        }
        return true
    }

    private fun bindCar() {
        binding.apply {
            nameCar.text = car.name
            brandCar.text = car.brand
            powerCar.text = car.power.toString() + " kW"
            doorCar.text = car.numberDoors.toString()
            fuelCar.text = car.fuel.toString()
            if (noSecondFuel(car)) {
                binding.icSecondFuelCar.visibility = View.VISIBLE
                binding.secondFuelCar.visibility = View.VISIBLE
            }
            secondFuelCar.text = car.secondFuel.toString()
            yearCar.text = car.productionYear.toString()
            placesCar.text = car.places.toString()
            colorCar.text = car.color.toString()
            kmCar.text = car.kM.toString() + " kM"
            deleteCar.setOnClickListener { showConfirmationDialog() }
            editCar.setOnClickListener { editCar() }
            if (imageCar != null) {
                imageCar.setImageBitmap(
                    Bitmap.createScaledBitmap(
                        BitmapFactory.decodeByteArray(
                            car.image, 0, car.image.size
                        ), 600, 250, false
                    )
                )
            } else {
                imageCar.setImageResource(R.drawable.ic_car)
            }
        }
    }


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

    private fun deleteCar() {
        viewModel.deleteCar(car)
        findNavController().navigateUp()
    }

    private fun editCar() {
        val action = CarDetailFragmentDirections.actionCarDetailFragmentToAddCarFragment(
            car.id,
            getString(R.string.edit_fragment_title)
        )
        this.findNavController().navigate(action)
    }
}