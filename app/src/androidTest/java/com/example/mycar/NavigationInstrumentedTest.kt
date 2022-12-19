package com.example.mycar

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.mycar.ui.fragment.AddCarFragment
import com.example.mycar.ui.fragment.CarListFragment
import org.mockito.Mockito

val mockNavController = Mockito.mock(NavController::class.java)
val themeApplication = R.style.Theme_MyCar

/**
 * Function that returns a fragment and a simulated NavController object
 */
fun mockAddCarFragment(): NavController {
    launchFragmentInContainer(themeResId = R.style.Theme_MyCar) {
        AddCarFragment().also { fragment ->
            fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                if (viewLifecycleOwner != null) {
                    Navigation.setViewNavController(fragment.requireView(), mockNavController)
                }
            }
        }
    }
    return mockNavController
}

/**
 * Function that starts from the main screen
 */
fun startCarListFragment(): NavController {
    launchFragmentInContainer<CarListFragment>(themeResId = themeApplication) {
        CarListFragment().also { fragment ->
            fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                if (viewLifecycleOwner != null) {
                    Navigation.setViewNavController(fragment.requireView(), mockNavController)
                }
            }
        }
    }
    return mockNavController
}