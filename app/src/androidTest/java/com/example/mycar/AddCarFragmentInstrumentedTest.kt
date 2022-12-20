package com.example.mycar

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddCarFragmentInstrumentedTest {
    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)
    val car = ListCar

    /**
     * Function for adding a car by taking the information from the ListCar list
     */
    @Test//IT WORKS ONLY ON PHYSICAL DEVICES
    fun addNewCarWithInternet() {
        clickId(R.id.add_car)
        Thread.sleep(1000)
        clickTextInputListBrand(R.id.brand_car_input, car[0].brand)
        Thread.sleep(3000)
        clickTextInputListModel(R.id.name_car_input, car[0].name)
        Thread.sleep(1000)
        clickCancelTextInputListBrand(R.id.brand_car_input, car[0].brand)
        Thread.sleep(3000)
        clickTextInputListBrand(R.id.brand_car_input, car[0].brand)
        Thread.sleep(3000)
        clickTextInputListModel(R.id.name_car_input, car[0].name)
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
        clickTextInputListFuel(R.id.fuel_car_input, car[0].fuel)
        Thread.sleep(2000)
        clickCancelTextInputListFuel(R.id.fuel_car_input, car[0].fuel)
        Thread.sleep(2000)
        clickTextInputListFuel(R.id.fuel_car_input, car[0].fuel)
        Thread.sleep(2000)
        scrollTo(R.id.layout_add_new_car_with_connection)
        clickTextInputWriteString(R.id.second_fuel_car_input, car[0].secondFuel.toString())
        Thread.sleep(1000)
        hideKeyboard()
        Thread.sleep(1000)
        scrollTo(R.id.layout_add_new_car_with_connection)
        scrollTo(R.id.linearLayout2)
        scrollTo(R.id.linearLayout)
        clickId(R.id.save_btn)
        Thread.sleep(1000)
    }

    /**
     * Function to control the display of the error image when the internet is disabled and when it is activated to display one of the fragment items (save button)
     */
    @Test//IT WORKS
    fun addNewControlInternet() {
        enableWifi(false)
        enableCellularData(false)
        clickId(R.id.add_car)
        checkIfVisible(R.id.image_error_connection)
        enableWifi(true)
        enableCellularData(true)
        clickId(R.id.retry_again_error_connection)
        Thread.sleep(3000)
    }
}