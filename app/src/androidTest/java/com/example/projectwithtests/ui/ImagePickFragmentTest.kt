package com.example.projectwithtests.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.projectwithtests.LaunchFragmentInHiltContainer
import com.example.projectwithtests.R
import com.example.projectwithtests.ShoppingFragmentFactory
import com.example.projectwithtests.adapters.ImageAdapter
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
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ImagePickFragmentTest{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltrule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory

    @Before
    fun setup(){
        hiltrule.inject()
    }

    @Test
    fun click_toimagenavigatetoaddfragement(){
        /** * here is the thing that espresso is a very fast testing library that's why we should remove any type of animations we added
         * unless it will be flaky test
         *  Our [DiffUtil] library causes to add the animation hence we need to remove the run the following steps/commands
         *on the terminal :->
         *  *   adb shell settings put global window_animation_scale 0
         * * adb shell settings put global transition_animation_scale 0
         * * adb shell settings put global animator_duration_scale 0
         */
        val  navController = mock(NavController::class.java)
        val ImageUrl = "Test"
        val Testviewmodel = ShoppingViewModel(FakeRepository2())
        LaunchFragmentInHiltContainer<ImagePickFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(),navController)
            imageAdapter.images = listOf(ImageUrl)
            viewModel = Testviewmodel
        }
        //performing click action on the recyclerview
        onView(withId(R.id.rvImages)).perform(RecyclerViewActions.actionOnItemAtPosition<ImageAdapter.ImageViewHolder>(
            0,
            click()
        )
        )
        verify(navController).popBackStack()
        assertThat(Testviewmodel.curImageUrl.getOrAwaitValue()).isEqualTo(ImageUrl)
    }
}