package com.example.mycar

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.example.mycar.ui.fragment.AddCarFragment
import com.example.mycar.ui.viewmodel.CarViewModel
import com.example.mycar.ui.viewmodel.CarViewModelFactory
import org.hamcrest.Matchers.hasToString
import org.hamcrest.Matchers.startsWith
import org.junit.Assert.assertEquals
import org.mockito.Mockito


//To review to see if it can serve
fun goToAddFragmentFromHome() {
    onView(withId(R.id.action_carListFragment_to_addCarFragment))
        .perform(click())
}

fun goToHomeFromAddFragment() {
    onView(withId(R.id.action_addCarFragment_to_carListFragment))
        .perform(click())
}

fun goToDetailFromHome() {
    onView(withId(R.id.action_carListFragment_to_carDetailFragment))
        .perform(click())
}

fun goToAddFromDetailFragment() {
    onView(withId(R.id.action_carDetailFragment_to_addCarFragment))
        .perform(click())
}

fun goToHome() {
    onView(withId(R.id.carListFragment))
        .perform(click())
}

fun goToAdd() {
    onView(withId(R.id.addCarFragment))
        .perform(click())
}

fun goToDetail() {
    onView(withId(R.id.carDetailFragment))
        .perform(click())
}

fun clickAddNewCar() {
    onView(withId(R.id.add_car))
        .perform(click())
}

fun clickFloatingDetail() {
    /*onView(withId(R.id.detail_car))
        .perform(click())
        .check(matches(isDisplayed()))*/
    clickId(R.id.detail_car)
}

fun clickEditFloatingDetail() {
    /*onView(withId(R.id.edit_car))
        .perform(click())
        .check(matches(isDisplayed()))*/
    clickId(R.id.edit_car)
}

fun clickShareFloatingDetail() {
    /*onView(withId(R.id.share_car))
        .perform(click())
        .check(matches(isDisplayed()))*/
    clickId(R.id.share_car)
}

fun clickDeleteFloatingDetail() {
    /*onView(withId(R.id.delete_car))
        .perform(click())
        .check(matches(isDisplayed()))*/
    clickId(R.id.delete_car)
}

fun clickId(idInput: Int){
    onView(withId(idInput))
        .perform(click())
}

fun clickString(input: String){
    onView(withText(input))
        .perform(click())
}

//TODO: Review this function
fun navigateToListToAdd() {
    goToHome()
    clickAddNewCar()
    goToAdd()
}

fun navigateToListToDetail() {
    goToHome()
    goToDetail()
    clickFloatingDetail()
}

fun navigateToListToDetailEdit() {
    goToHome()
    goToDetail()
    clickFloatingDetail()
    clickEditFloatingDetail()
}

fun navigateToListToDetailShare() {
    goToHome()
    goToDetail()
    clickFloatingDetail()
    clickShareFloatingDetail()
}

fun navigateToListToDetailDelete() {
    goToHome()
    goToDetail()
    clickFloatingDetail()
    clickDeleteFloatingDetail()
}

fun addNewCar() {
    navigateToListToAdd()
}

fun updateCar() {
    navigateToListToDetailEdit()
}

fun shareCar() {
    navigateToListToDetailShare()
}

fun deleteCar() {
    navigateToListToDetailDelete()
}

fun clickTextInputWriteString(idInput: Int, string: String) {
    onView(withId(idInput))
        .perform(typeText(string))
}

//to review
fun clickTextInputListBrand(idInput: Int, brand: String) {
    clickId(idInput)
    // Open alert dialog
    val listBrandCar = ListBrand
    val items = arrayOfNulls<CharSequence>(listBrandCar.size)
    for (i in listBrandCar.indices) {
        items[i] = listBrandCar[i]
    }
    onView(withText(items.size)).perform(click())
    Thread.sleep(5000)
    // Select model?
    onView(withText(brand)).perform(click())
}

fun clickTextInputListModel(idInput: Int, model: String) {
    clickId(idInput)
    onData(hasToString(startsWith(model))).inAdapterView(withId(R.id.name_car)).perform(click())
}

fun clickTextInputListFuel(idInput: Int, fuel: String) {
    clickId(idInput)
    onData(hasToString(startsWith(fuel))).inAdapterView(withId(R.id.fuel_car)).perform(click())
}

/**
 * Function that takes a boolean value as input to enable and disable wifi
 * via a shell command through the uiAutomation object
 */
fun enableWifi(enable: Boolean){
    when (enable)
    {
        true -> InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc wifi enable")
        false -> InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc wifi disable")
    }
}

/**
 * Function that takes a boolean value as input to enable and disable cellular data
 * via a shell command through the uiAutomation object
 */
fun enableCellularData(enable: Boolean){
    when (enable)
    {
        true -> InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc data enable")
        false -> InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc data disable")
    }
}

/**
 * Function that takes an ID as input and checks whether or not it is visible on the screen
 */
fun checkIfVisible(id: Int){
    onView(withId(id)).check(matches(ViewMatchers.isDisplayed()))
}

/**
 * Function used to verify that the current location is the expected location by taking the current id and the destination id
 */
fun check_current_location_is_expected(currentLocation: Int?, expectedLocation: Int) {
    assertEquals(
        currentLocation,
        expectedLocation
    )
}

/**
 * Support function that is used to simulate clicking on a specific tab in a RecyclerView view
 */
fun clickItem(idCard: Int) {
    onView(withId(R.id.recycler_view)).perform(
        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(idCard, click())
    )
}

/**
 * Function to hide the virtual keyboard on an Android device
 */
fun hideKeyboard() {
    onView(ViewMatchers.isRoot()).perform(closeSoftKeyboard())
}

/**
 * Function to scroll view
 */
fun scrollTo(id: Int) {
    onView(withId(id))
        .perform(scrollTo())
        .check(matches(isDisplayed()))
}

/**
 * Function for clear text input
 */
fun clearTextInput(idTextInput: Int) {
    onView(withId(idTextInput)).perform(clearText())
}