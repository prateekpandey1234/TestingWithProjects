package com.example.projectwithtests.ui

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.projectwithtests.LaunchFragmentInHiltContainer
import com.example.projectwithtests.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ShoppingFragmentTest{
    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)
    @Before
    fun setup(){
        hiltAndroidRule.inject()
    }
/**A mockito object is very similar to the class initiated with it , it will have same function signatures and
 * can also edit the return types of the function and access the parameters that are passed through the function
 * */
    @Test
    fun clickaddFAB_navigatetoImagePickFragment(){
        val navigationcontroller = mock(NavController::class.java)
        LaunchFragmentInHiltContainer<ShoppingFragment> {
            Navigation.setViewNavController(requireView(),navigationcontroller)//setting up our mock navcontroller
        }
//down here we are using espresso library to test our ui elements
    //first we access the view using the following method and add the perform method to do any action we want
        onView(withId(R.id.fabAddShoppingItem)).perform(click())
    //the following method allows us to verify whether the correct parameters were passed to our navigate function inside
    //our mock object .... here the parameter is same as we passed in our fragment
        verify(navigationcontroller).navigate(
            ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment()
        )
    //this following when method of mockito library allows us to change the return type of any function we wanted
    //inside our functions in mockito object we created
        `when`(navigationcontroller.popBackStack()).thenReturn(true)
    }

}