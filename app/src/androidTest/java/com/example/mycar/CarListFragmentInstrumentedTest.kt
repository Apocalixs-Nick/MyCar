package com.example.mycar

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.example.mycar.data.MyCarDao
import com.example.mycar.data.MyCarDatabase
import kotlinx.coroutines.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CarListFragmentInstrumentedTest {
    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)

    lateinit var carDb: MyCarDatabase
    lateinit var carDao: MyCarDao
    val context = ApplicationProvider.getApplicationContext<Context>()


    @Before
    fun setUpDatabase() {
        carDb = Room.inMemoryDatabaseBuilder(context, MyCarDatabase::class.java).build()
        carDao = carDb.myCarDao()
    }

    @After
    fun closeDatabase() {
        carDb.close()
    }

    /**
     * Test to verify the operation of insert() and getCars()
     */
    @Test
    fun addDatabase() = runBlocking {
        val car = ListCarDao[0]
        carDao.insert(car)
        carDao.getCars().test {
            val list = awaitItem()
            suspend {
                assert(list.contains(car))
            }
            cancel()
        }
    }
    /**
     * Test to verify the operation of insert() and getCars()
     * and update the car with the return in the stream
     */
    @Test
    fun updateCarShouldReturnIteminFlow() = runBlocking {
        val carRandom1 = ListCarDao[0]
        val carRandom2 = ListCarDao[1]
        val carRandom3 = ListCarDao[2]
        carDao.insert(carRandom1)
        carDao.insert(carRandom2)
        carDao.getCars().test {
            val list = awaitItem()
            suspend {
                assert(list.size == 2)
                assert(list.contains(carRandom3))
            }
            cancel()
        }
    }

    /**
     * Test to verify the operation of insert() and getCars()
     * and delete the car that should not be in the stream
     */
    @Test
    fun deletedCarShouldNotBePresentInFlow() = runBlocking {
        val car = ListCarDao[0]
        val car1 = ListCarDao[1]
        carDao.insert(car)
        carDao.insert(car1)
        carDao.delete(car)
        carDao.getCars().test {
            val list = awaitItem()
            suspend {
                assert(list.size == 1)
                assert(list.contains(car1))
            }
            cancel()
        }
    }

}