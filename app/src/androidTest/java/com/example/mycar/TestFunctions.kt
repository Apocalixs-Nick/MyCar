package com.example.mycar

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId


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
    onView(withId(R.id.detail_car))
        .perform(click())
}

fun clickEditFloatingDetail() {
    onView(withId(R.id.edit_car))
        .perform(click())
}

fun clickShareFloatingDetail() {
    onView(withId(R.id.share_car))
        .perform(click())
}

fun clickDeleteFloatingDetail() {
    onView(withId(R.id.delete_car))
        .perform(click())
}

fun clickId(idInput: Int){
    onView(withId(idInput))
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