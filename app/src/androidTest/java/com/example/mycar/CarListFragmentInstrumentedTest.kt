package com.example.mycar

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mycar.data.MyCarDatabase
import com.example.mycar.model.MyCar
import kotlinx.coroutines.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
class CarListFragmentInstrumentedTest {
    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)
    //TODO:Verify navigation between fragments
    val database = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        MyCarDatabase::class.java
    ).allowMainThreadQueries().build()
    val myCarDao = database.myCarDao()

    @Test//IDK NOW
    fun addAndDisplayingVehicleInTheHome() = runBlocking {
        clickId(R.id.add_car)
        val car = MyCar(
            name = "Panda",
            brand = "FIAT",
            power = 45,
            fuel = "Gasoline",
            secondFuel = "",
            numberDoors = 5,
            productionYear = 2012,
            image = R.id.ic_brand_car.toString().encodeToByteArray(),
            places = 5,
            color = "Yellow",
            kM = 2580
        )
        myCarDao.insert(car)
        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            myCarDao.getCars().collect {
            }
        }
        withContext(Dispatchers.IO) {
            latch.await()
        }
        job.cancelAndJoin()
        //Continue to see if you view the vehicle?
    }

}