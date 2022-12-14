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