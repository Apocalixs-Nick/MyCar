package com.example.mycar

import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationInstrumentedTest {
    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)

    val navController = TestNavHostController(
        ApplicationProvider.getApplicationContext()
    )

    // TODO: To be completed and reviewed if it is the right approach
    @Test
    fun navigateTest() {
        val navigateItems = setOf (
            R.id.action_carListFragment_to_addCarFragment,
            R.id.action_addCarFragment_to_carListFragment,
            R.id.action_carListFragment_to_carDetailFragment,
            R.id.action_carDetailFragment_to_addCarFragment
        )

        navigateItems.forEach {
            onView(withId(it))
                .perform(click())
        }
    }
}