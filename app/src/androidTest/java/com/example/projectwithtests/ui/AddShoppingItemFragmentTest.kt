package com.example.projectwithtests.ui

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.pressBack
import androidx.test.filters.MediumTest
import com.example.projectwithtests.LaunchFragmentInHiltContainer
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class AddShoppingItemFragmentTest{
    @get:Rule
    val hiltAndroidTest = HiltAndroidRule( this)
    @Before
    fun setup(){
        hiltAndroidTest.inject()
    }

    @Test
    fun onbackbuttonpressednavigatetofargment(){
        val navController = mock(NavController::class.java)
        LaunchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(),navController)
        }
        //use the following method to test and run the back button
        pressBack()
        verify(navController).popBackStack()
    }
    //to test whether correct parameter was passed or not in view model when clicked back
    @Test
    fun Onnavigateback_viewmdoelurlsetempty(){
        var currurl : String? = "1"
        val navController = mock(NavController::class.java)
        LaunchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(),navController)
            viewModel.setCurImageUrl("4545")
            currurl = viewModel.curImageUrl.value
        }
        pressBack()
        verify(navController).popBackStack()
        assertThat(currurl).isEqualTo(null)
    }
}