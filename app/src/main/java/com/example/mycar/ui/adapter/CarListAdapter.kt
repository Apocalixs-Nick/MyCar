package com.example.mycar.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mycar.databinding.ListItemCarBinding
import com.example.mycar.model.MyCar

class CarListAdapter(private val clickListener: (MyCar) -> Unit) :
    ListAdapter<MyCar, CarListAdapter.CarViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        //val layoutInflater = LayoutInflater.from(parent.context)
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
        holder.bind(car)
    }

    class CarViewHolder(private var binding: ListItemCarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(car: MyCar) {
            binding.apply {
                nameCar.text = car.name
                brandCar.text = car.brand
                yearCar.text = car.productionYear.toString()
                fuelCar.text = car.fuel
                powerCar.text = car.power.toString()
            }
            /*binding.car = car
            binding.executePendingBindings()*/
        }
    }

    //DiffCallback : DiffUtil.ItemCallback<MyCar>()
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