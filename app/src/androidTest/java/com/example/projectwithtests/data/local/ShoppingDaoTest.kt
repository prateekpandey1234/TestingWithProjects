package com.example.projectwithtests.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import org.junit.Before
import org.junit.runner.RunWith
/**So what does this annotation really do that it allows us to tell jvm that it's a instrumented test and needs to run on jvm and
 * emulator for testing
 * * If a JUnit class or its parent class is annotated with @RunWith, JUnit framework invokes the specified class as a test
 * runner instead of running the default runner.
 * * In Junit, test suite allows us to aggregate all test cases from multiple classes in one place and run it together.*/
/** * The SmallTest annotation refers to the Unit tests we run here
 * * MediumTest -> annotation for IIntegration tests or Medium tests
 * * Largetests -> annotation for End-to-End tests we do for our Whole application*/
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {
    private lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
//We should prefer using Room.inMemoryDataBaseBuilder because it allows us to store data for our
//Database inside the RAM not storing as persistent storage or like a real Database
//it also allows to avoid creating new databases for every test cases we are running
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.shoppingDao()
    }
}