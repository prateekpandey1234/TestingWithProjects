package com.example.projectwithtests.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
/**So what does this annotation really do that it allows us to tell jvm that it's a instrumented test and needs to run on jvm and
 * emulator for testing
 * * If a JUnit class or its parent class is annotated with @RunWith, JUnit framework invokes the specified class as a test
 * runner instead of running the default runner.
 * * In Junit, test suite allows us to aggregate all test cases from multiple classes in one place and run it together.
* * The SmallTest annotation refers to the Unit tests we run here
 * * MediumTest -> annotation for IIntegration tests or Medium tests
 * * Largetests -> annotation for End-to-End tests we do for our Whole application*/
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@SmallTest
class ShoppingDaoTest {
//so basically we use the following code to specifically tell junit that we want to run all our test cases inside the Main Thread
//And not allow any Live Data to run Asynchronous  ... this method allows to run the methods of this class one after another in
// A single Thread which is here our Main Thread

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
//We should prefer using Room.inMemoryDataBaseBuilder because it allows us to store data for our
//Database inside the RAM not storing as persistent storage or like a real Database
//it also allows to avoid creating new databases for every test cases we are running
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),//Used to get Reference for Context of our Application
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries().build()//we use Main Thread as common Thread to run our Tests because we want to run the test cases one after another
        dao = database.shoppingDao()
    }
    @After
    fun teardown() {
        database.close()
    }
    //we use runBlockingTest when we want to run our tests on Main thread

    @Test
    fun insertShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem("name", 1, 1f, "url", id = 1)
        dao.insertShoppingItem(shoppingItem)
//so Basically our variable down here is a LiveData and it runs Asynchronously with our MainThread which we don't want
//Hence we use a Google file or Kotlin file downloaded from the Internet and paste it inside the root directory of our test kt file
//it allows to test our LiveData files and if LiveData is not given under 2 sec it will return  error
        val allShoppingItems = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems).contains(shoppingItem)
    }
    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem("name", 1, 1f, "url", id = 1)
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)

        val allShoppingItems = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems).doesNotContain(shoppingItem)
    }
    @Test
    fun observeTotalPriceSum() = runBlockingTest {
        val shoppingItem1 = ShoppingItem("name", 2, 10f, "url", id = 1)
        val shoppingItem2 = ShoppingItem("name", 4, 5.5f, "url", id = 2)
        val shoppingItem3 = ShoppingItem("name", 0, 100f, "url", id = 3)
        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)

        val totalPriceSum = dao.observeTotalPrice().getOrAwaitValue()

        assertThat(totalPriceSum).isEqualTo(2 * 10f + 4 * 5.5f)
    }

}
