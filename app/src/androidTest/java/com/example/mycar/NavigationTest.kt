package com.example.mycar

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mycar.ui.fragment.CarListFragment
import com.example.mycar.ui.fragment.CarListFragmentDirections
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class NavigationTest {
    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)

    val navController = TestNavHostController(
        ApplicationProvider.getApplicationContext()
    )

    val theme = R.style.Theme_MyCar

    //IT WORKS
    @Test
    fun navigationFromHomeFragmentToAddFragment() {
        val homeFragmentScenario =
            launchFragmentInContainer<CarListFragment>(themeResId = theme)
        homeFragmentScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        clickId(R.id.add_car)
        check_current_location_is_expected(
            navController.currentDestination?.id,
            R.id.addCarFragment
        )
    }

    //IT WORKS
    @Test
    fun navigationFromHomeFragmentToCarDetailFragment() {
        val mockNavController = startCarListFragment()
        clickItem(0)
        verify(mockNavController).navigate(
            CarListFragmentDirections.actionCarListFragmentToCarDetailFragment(
                1
            )
        )
    }
}