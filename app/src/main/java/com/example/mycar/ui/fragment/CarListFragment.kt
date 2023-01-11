package com.example.mycar.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycar.BaseApplication
import com.example.mycar.R
import com.example.mycar.databinding.FragmentCarListBinding
import com.example.mycar.network.logo.MyCarLogo
import com.example.mycar.ui.adapter.CarListAdapter
import com.example.mycar.ui.viewmodel.CarViewModel
import com.example.mycar.ui.viewmodel.CarViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [CarListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CarListFragment : Fragment() {

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

    private var _binding: FragmentCarListBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        try {
            // Inflate the layout for this fragment
            _binding = FragmentCarListBinding.inflate(inflater, container, false)
            return binding.root
        } catch (e: Exception) {
            Log.e("ErrorList", "onCreateViewCarListFragment", e)
            throw e
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            val adapter =
                CarListAdapter(logoDataApi = viewModel.logoDataApi, clickListener = { car ->
                    val action = CarListFragmentDirections
                        .actionCarListFragmentToCarDetailFragment(
                            car.id
                        )
                    findNavController().navigate(action)
                })

            val observer = Observer<List<MyCarLogo>> {
                binding.recyclerView.adapter = adapter
            }
            viewModel.logoDataApi.observe(viewLifecycleOwner, observer)
            viewModel.allCar.observe(this.viewLifecycleOwner) { cars ->
                cars.let {
                    adapter.submitList(it)
                }
            }
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            binding.recyclerView.adapter = adapter
            binding.add.setOnClickListener {
                val addAction = CarListFragmentDirections.actionCarListFragmentToAddCarFragment(
                    id = 0,
                    getString(R.string.add_car)
                )
                this.findNavController().navigate(
                    addAction
                )
            }
            binding.linkedin.setOnClickListener {
                val url = "https://www.linkedin.com/in/nicola-piccirillo-05a76b254"
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }
            binding.git.setOnClickListener {
                val url = "https://github.com/Apocalixs-Nick"
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }
        } catch (e: Exception) {
            Log.e("ErrorList", "onViewCreateCarListFragment", e)
            throw e
        }

        binding.addCar.setOnClickListener {
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
            binding.add.visibility = View.VISIBLE
            binding.git.visibility = View.VISIBLE
            binding.linkedin.visibility = View.VISIBLE
            binding.add.isEnabled = true
            binding.git.isEnabled = true
            binding.linkedin.isEnabled = true
        } else {
            binding.add.visibility = View.GONE
            binding.git.visibility = View.GONE
            binding.linkedin.visibility = View.GONE
            binding.add.isEnabled = false
            binding.git.isEnabled = false
            binding.linkedin.isEnabled = false
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding.add.startAnimation(fromBottom)
            binding.git.startAnimation(fromBottom)
            binding.linkedin.startAnimation(fromBottom)
            binding.addCar.startAnimation(rotateOpen)
        } else {
            binding.add.startAnimation(toBottom)
            binding.git.startAnimation(toBottom)
            binding.linkedin.startAnimation(toBottom)
            binding.addCar.startAnimation(rotateClose)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clicked = false
    }
}