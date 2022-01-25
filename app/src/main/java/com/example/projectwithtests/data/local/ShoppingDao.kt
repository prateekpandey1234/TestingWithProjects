package com.example.projectwithtests.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Marks the class as a Data Access Object.
 * * Data Access Objects are the main classes where you define your database interactions. They can include a variety of query methods.
 * * The class marked with @Dao should either be an interface or an abstract class. At compile time, Room will generate an implementation of this class when it is referenced by a Database.
 * * An abstract @Dao class can optionally have a constructor that takes a Database as its only parameter.
 * * It is recommended to have multiple Dao classes in your codebase depending on the tables they touch.*/
@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)
/**
 * we don't use suspend functions in Room with livedata because it will not work for room as the LiveData already
 * runs asynchronously */
    @Query("SELECT * FROM shopping_items")
    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    @Query("SELECT SUM(price * amount) FROM shopping_items")
    fun observeTotalPrice(): LiveData<Float>
}