package com.example.mycar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.mycar.data.MyCarDao
import com.example.mycar.model.MyCar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarViewModel(private val myCarDao: MyCarDao) : ViewModel() {

    //Variable for the acquisition of all the cars of the database
    val allCar: LiveData<List<MyCar>> = myCarDao.getCars().asLiveData()

    fun isValidEntry(
        name: String,
        brand: String,
        power: String,
        numberDoors: String,
        productionYear: String
    ): Boolean {

        if ((name.isBlank() || brand.isBlank() || power.isBlank() || numberDoors.isBlank() || productionYear.isBlank() )) {
            return false
        }
        return true
    }

    /**
     * Function to recover a car via id
     */
    fun retrieveCar(id: Long): LiveData<MyCar> {
        return myCarDao.getCar(id).asLiveData()
    }

    /**
     * Function for add a new car
     */
    fun addCar(name: String, brand: String, power: String, numberDoors: String, productionYear: String) {

        val car = MyCar(
            name = name,
            brand = brand,
            power = power.toInt(),
            numberDoors = numberDoors.toInt(),
            productionYear = productionYear.toInt()
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                myCarDao.insert(car)
            } catch (e: Exception) {
                Log.e("NOT ADD CAR", e.toString())
            }
        }
    }

    /**
     * Function for update a car
     */
    fun updateCar(
        id: Long,
        name: String,
        brand: String,
        power: String,
        numberDoors: String,
        productionYear: String
    ) {

        val car = MyCar(
            id = id,
            name = name,
            brand = brand,
            power = power.toInt(),
            numberDoors = numberDoors.toInt(),
            productionYear = productionYear.toInt()
        )

        viewModelScope.launch {
            myCarDao.update(car)
        }
    }

    /**
     * Function for delete a car
     */
    fun deleteCar(car: MyCar) {
        viewModelScope.launch(Dispatchers.IO) {
            myCarDao.delete(car)
        }
    }


    /*fun isValidEntryInt(power: Int, numberDoors: Int, productionYear: Int): Boolean {
        var state: Boolean
        if (power != 0) {
            if (numberDoors != 0) {
                if (productionYear != 0) {
                        state = true
                } else {
                        state = false
                }
            } else {
                state = false
            }
        } else {
            state = false
        }
        return state
    }*/
}


class CarViewModelFactory(private val myCarDao: MyCarDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarViewModel(myCarDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}