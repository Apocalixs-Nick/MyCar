package com.example.mycar.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mycar.BaseApplication
import com.example.mycar.R
import com.example.mycar.databinding.FragmentAddCarBinding
import com.example.mycar.model.MyCar
import com.example.mycar.ui.viewmodel.CarViewModel
import com.example.mycar.ui.viewmodel.CarViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        val id = navigation.id
        if (id > 0) {
            viewModel.retrieveCar(id).observe(this.viewLifecycleOwner) { selectCar ->
                car = selectCar
                bindCar(car)
            }

            /*binding.deleteBtn.visibility = View.VISIBLE
            binding.deleteBtn.setOnClickListener {
                deleteCar(car)
            }*/
        } else {
            binding.saveBtn.setOnClickListener {
                addCar()
            }
        }
    }

    //to review
    private fun addCar() {
        if (isValidEntry()) {
            viewModel.addCar(
                name =binding.nameCarInput.text.toString(),
                brand = binding.brandCarInput.text.toString(),
                power = binding.powerCarInput.text.toString(),
                numberDoors = binding.doorsCarInput.text.toString(),
                fuel =binding.fuelCarInput.text.toString(),
                productionYear = binding.yearCarInput.text.toString()
            )
            findNavController().navigate(
                R.id.action_addCarFragment_to_carListFragment
            )
        }
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
                productionYear = binding.yearCarInput.text.toString()
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
            binding.yearCarInput.text.toString()
        )
    }

    /*private fun isValidEntryInt() = viewModel.isValidEntryInt {
            binding.doorsCarInput.text.toString()
            binding.powerCarInput.text.toString()
            binding.yearCarInput.text.toString()
    }*/

    private fun deleteCar(car: MyCar) {
        viewModel.deleteCar(car)
        findNavController().navigate(
            R.id.action_addCarFragment_to_carListFragment
        )
    }

    private fun bindCar(car: MyCar) {
        binding.apply {
            nameCarInput.setText(car.name, TextView.BufferType.SPANNABLE)
            brandCarInput.setText(car.brand, TextView.BufferType.SPANNABLE)
            doorsCarInput.setText(car.numberDoors.toString(), TextView.BufferType.SPANNABLE)
            fuelCarInput.setText(car.fuel, TextView.BufferType.SPANNABLE)
            powerCarInput.setText(car.power.toString(), TextView.BufferType.SPANNABLE)
            yearCarInput.setText(car.productionYear.toString(), TextView.BufferType.SPANNABLE)
            saveBtn.setOnClickListener {
                updateCar()
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
}