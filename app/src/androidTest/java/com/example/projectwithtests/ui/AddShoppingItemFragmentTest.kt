package com.example.projectwithtests.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.projectwithtests.LaunchFragmentInHiltContainer
import com.example.projectwithtests.R
import com.example.projectwithtests.ShoppingFragmentFactory
import com.example.projectwithtests.ShoppingFragmentFactory_Factory
import com.example.projectwithtests.data.local.ShoppingItem
import com.example.projectwithtests.repository.FakeRepository2
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class AddShoppingItemFragmentTest{
    @get:Rule
    val hiltAndroidTest = HiltAndroidRule( this)
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Inject
    lateinit var fragmentfactoryFactory: ShoppingFragmentFactory
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

    @Test
    fun clickinsertintoDb_shoppingitem(){
        val testviewmodel = ShoppingViewModel(FakeRepository2())
        LaunchFragmentInHiltContainer<AddShoppingItemFragment>(fragmentFactory = fragmentfactoryFactory) {
            viewModel = testviewmodel
        }

        onView(withId(R.id.etShoppingItemName)).perform(replaceText("NAME"))
        onView(withId(R.id.etShoppingItemAmount)).perform(replaceText("44"))
        onView(withId(R.id.etShoppingItemPrice)).perform(replaceText("5.5"))
        onView(withId(R.id.btnAddShoppingItem)).perform(click())
        //checking whether we added shopping item to the list or not
        assertThat(testviewmodel.shoppingItems.getOrAwaitValue())
            .contains(ShoppingItem("NAME",44,5.5f,""))
    }
}