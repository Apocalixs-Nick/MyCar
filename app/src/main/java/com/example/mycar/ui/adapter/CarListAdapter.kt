package com.example.mycar.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.loadAny
import com.example.mycar.R
import com.example.mycar.databinding.ListItemCarBinding
import com.example.mycar.model.MyCar
import com.example.mycar.network.logo.MyCarLogo
import com.example.mycar.ui.setAndGetUriByBrandParsingListOfLogoAndImageView

class CarListAdapter(private val clickListener: (MyCar) -> Unit, private val logoDataApi: LiveData<List<MyCarLogo>>) :
    ListAdapter<MyCar, CarListAdapter.CarViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        return CarViewHolder(
            ListItemCarBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = getItem(position)
        holder.itemView.setOnClickListener {
            clickListener(car)
        }
        holder.bind(car, logoDataApi)
    }

    class CarViewHolder(private var binding: ListItemCarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(car: MyCar, logoDataApi: LiveData<List<MyCarLogo>>) {
            binding.apply {
                setAndGetUriByBrandParsingListOfLogoAndImageView(logoDataApi.value, car.brand, logoCar)
                nameCar.text = car.name
                brandCar.text = car.brand
                yearCar.text = car.productionYear.toString()
                fuelCar.text = car.fuel
                if (noSecondFuel(car)) {
                    secondFuelCar.visibility = View.VISIBLE
                }
                secondFuelCar.text = car.secondFuel
                powerCar.text = car.power.toString() + " kW"
            }
        }

        private fun noSecondFuel(car: MyCar): Boolean {
            if (car.secondFuel?.isBlank() == true) {
                return false
            }
            return true
        }

    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<MyCar>() {
            override fun areItemsTheSame(oldItem: MyCar, newItem: MyCar): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: MyCar, newItem: MyCar): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}