package com.example.mycar

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mycar.ui.fragment.CarListFragmentDirections
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class CarDetailFragmentInstrumentedTest {
    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)
    //TODO:Check the use of buttons

    @Test//TO REVIEW
    fun clickFabEdit() {
        val mockNavController = startCarListFragment()
        clickItem(0)
        verify(mockNavController).navigate(
            CarListFragmentDirections.actionCarListFragmentToCarDetailFragment(
                1
            )
        )
        Thread.sleep(5000)
        //clickFloatingDetail()
        clickId(R.id.detail_car)
        clickEditFloatingDetail()
    }

    @Test//TO REVIEW
    fun clickFabShare() {
        goToDetail()
        clickFloatingDetail()
        clickShareFloatingDetail()
    }

    @Test//TO REVIEW
    fun clickFabDelete() {
        goToDetail()
        clickFloatingDetail()
        clickDeleteFloatingDetail()
        clickId(R.string.yes)
    }
}