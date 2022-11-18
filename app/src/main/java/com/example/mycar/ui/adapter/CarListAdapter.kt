package com.example.mycar.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mycar.databinding.ListItemCarBinding
import com.example.mycar.model.MyCar

class CarListAdapter(private val clickListener: (MyCar) -> Unit) : ListAdapter<MyCar, CarListAdapter.CarViewHolder>(DiffCallback){

    class CarViewHolder(private var binding: ListItemCarBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(car: MyCar) {
            binding.car = car
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<MyCar>() {
        override fun areItemsTheSame(oldItem: MyCar, newItem: MyCar): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MyCar, newItem: MyCar): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CarViewHolder(
            ListItemCarBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = getItem(position)
        holder.itemView.setOnClickListener{
            clickListener(car)
        }
        holder.bind(car)
    }
}