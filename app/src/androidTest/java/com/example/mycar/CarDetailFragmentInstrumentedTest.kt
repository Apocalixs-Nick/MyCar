package com.example.mycar

import android.app.AlertDialog
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeTextIntoFocusedView
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.coroutines.coroutineContext

@RunWith(AndroidJUnit4::class)
class CarDetailFragmentInstrumentedTest {
    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var uiDevice: UiDevice

    @Before
    fun setup(){
        uiDevice = UiDevice.getInstance(getInstrumentation())
    }

    @Test//IT WORKS
    fun clickDetail() {
        clickItem(2)
        clickId(R.id.detail_car)
    }

    @Test//IT WORKS WITH NOTIFICATION
    fun clickFabEditWithNotification() {
       /*val mockNavController = startCarListFragment()
        clickItem(0)
        verify(mockNavController).navigate(
            CarListFragmentDirections.actionCarListFragmentToCarDetailFragment(
                1
            )
        )
        clickFloatingDetail()*/
        clickDetail()
        clickId(R.id.edit_car)
        //clickEditFloatingDetail()
        scrollTo(R.id.layout_add_new_car_with_connection)
        clearTextInput(R.id.km_car_input)
        clickTextInputWriteString(R.id.km_car_input, "150000")
        hideKeyboard()
        scrollTo(R.id.layout_add_new_car_with_connection)
        scrollTo(R.id.linearLayout2)
        scrollTo(R.id.linearLayout)
        clickId(R.id.save_btn)
        /*clickDetail()
        Thread.sleep(3000)*/
        uiDevice.pressHome()
        uiDevice.openNotification()
        uiDevice.wait(androidx.test.uiautomator.Until.hasObject(By.textContains("Panda")), 5)
    }

    @Test//IT WORKS
    fun clickFabShare() {
        /*val mockNavController = startCarListFragment()
        clickItem(0)
        verify(mockNavController).navigate(
            CarListFragmentDirections.actionCarListFragmentToCarDetailFragment(
                1
            )

        )

        clickFloatingDetail()
        clickShareFloatingDetail()*/

        clickDetail()
        clickId(R.id.share_car)
    }

    @Test//IT WORKS
    fun clickFabDelete() {
        /*val mockNavController = startCarListFragment()
        clickItem(0)
        verify(mockNavController).navigate(
            CarListFragmentDirections.actionCarListFragmentToCarDetailFragment(
                1
            )
        )
        clickFloatingDetail()
        clickDeleteFloatingDetail()
        clickId(R.string.yes)*/

        clickDetail()
        clickId(R.id.delete_car)
        onView(withText(R.string.yes)).perform(click())
    }

    @Test//IT WORKS
    fun clickFabNoDelete() {
        clickDetail()
        clickId(R.id.delete_car)
        onView(withText(R.string.no)).perform(click())
    }
}