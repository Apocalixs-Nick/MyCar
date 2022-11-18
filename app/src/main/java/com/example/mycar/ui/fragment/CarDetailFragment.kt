package com.example.mycar.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.mycar.BaseApplication
import com.example.mycar.R
import com.example.mycar.databinding.FragmentAddCarBinding
import com.example.mycar.databinding.FragmentCarDetailBinding
import com.example.mycar.model.MyCar
import com.example.mycar.ui.adapter.CarListAdapter
import com.example.mycar.ui.viewmodel.CarViewModel
import com.example.mycar.ui.viewmodel.CarViewModelFactory

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
            Log.e("ErrorDetails", "onCreateViewCarDetailsFragment", e);
            throw e;
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

    private fun bindCar() {
        binding.apply {
            nameCar.text = car.name
            brandCar.text = car.brand
            powerCar.text = car.power.toString()
            doorCar.text = car.numberDoors.toString()
            yearCar.text = car.productionYear.toString()
        }
    }
}