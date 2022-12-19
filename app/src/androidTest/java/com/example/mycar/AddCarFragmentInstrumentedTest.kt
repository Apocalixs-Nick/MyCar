package com.example.mycar

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mycar.ui.fragment.AddCarFragmentDirections
import com.example.mycar.ui.fragment.CarListFragment
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class AddCarFragmentInstrumentedTest {
    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)
    //TODO:Check the addition of a vehicle
    val car = ListCar
    val mockNavController = Mockito.mock(NavController::class.java)

    /**
     * Function for adding a car by taking the information from the ListCar list
     */
    @Test//TO REVIEW - IT WORKS BUT CRASHES AFTER ADDING
    fun addNewCarWithInternet() {
        clickId(R.id.add_car)
        clickTextInputWriteString(R.id.brand_car_input, car[0].brand)
        Thread.sleep(1000)
        clickTextInputWriteString(R.id.name_car_input, car[0].name)
        Thread.sleep(1000)
        clickTextInputWriteString(R.id.power_car_input, car[0].power.toString())
        Thread.sleep(1000)
        clickTextInputWriteString(R.id.doors_car_input, car[0].numberDoors.toString())
        Thread.sleep(1000)
        clickTextInputWriteString(R.id.year_car_input, car[0].productionYear.toString())
        Thread.sleep(1000)
        clickTextInputWriteString(R.id.places_car_input, car[0].places.toString())
        Thread.sleep(1000)
        clickTextInputWriteString(R.id.color_car_input, car[0].color)
        Thread.sleep(1000)
        hideKeyboard()
        Thread.sleep(1000)
        scrollTo(R.id.layout_add_new_car_with_connection)
        clickTextInputWriteString(R.id.km_car_input, car[0].kM.toString())
        Thread.sleep(1000)
        hideKeyboard()
        Thread.sleep(1000)
        scrollTo(R.id.layout_add_new_car_with_connection)
        clickTextInputWriteString(R.id.fuel_car_input, car[0].fuel)
        Thread.sleep(1000)
        hideKeyboard()
        Thread.sleep(1000)
        scrollTo(R.id.layout_add_new_car_with_connection)
        clickTextInputWriteString(R.id.second_fuel_car_input, car[0].secondFuel.toString())
        Thread.sleep(1000)
        hideKeyboard()
        Thread.sleep(1000)
        scrollTo(R.id.layout_add_new_car_with_connection)
        scrollTo(R.id.linearLayout2)
        scrollTo(R.id.linearLayout)
        clickId(R.id.save_btn)
        hideKeyboard()
        Thread.sleep(1000)
        scrollTo(R.id.layout_add_new_car_with_connection)
        Thread.sleep(1000)
        /*verify(mockNavController).navigate(
            AddCarFragmentDirections.actionAddCarFragmentToCarListFragment()
        )*/
    }

    /**
     * Function to control the display of the error image when the internet is disabled and when it is activated to display one of the fragment items (save button)
     */
    @Test//IT WORKS
    fun addNewControlInternet() {
        enableWifi(false)
        enableCellularData(false)
        //goToAdd()
        clickId(R.id.add_car)
        checkIfVisible(R.id.image_error_connection)
        enableWifi(true)
        enableCellularData(true)
        clickId(R.id.retry_again_error_connection)
        Thread.sleep(3000)
        //checkIfVisible(R.id.brand_car_input)
    }
}