package com.example.projectwithtests.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projectwithtests.data.local.ShoppingItem
import com.example.projectwithtests.data.remote.responses.ImageResponse
import com.example.projectwithtests.other.Resource

/**
 * So basically we create this fake repository for testing our view models how they work with our repositories
 * [DefaultRepository] is our main repository*/
class FakeRepository2 :ShoppingRepository {
    /**we use [ShoppingRepository] to test our main repo [DefaultRepository]*/
    private val shoppingItems = mutableListOf<ShoppingItem>()
    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)
    private val observableTotalPrice = MutableLiveData<Float>()
    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }
    /**adding our data and the price inside our database as list*/
    private fun refreshLiveData() {
        observableShoppingItems.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPrice())
    }
    private fun getTotalPrice(): Float {
        return shoppingItems.sumOf { it.price.toDouble() }.toFloat()
    }
    /**adding and deleting data inside the list */
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
     }
    /**changing our mutable live data list according to the operations performed*/
    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return observableShoppingItems
     }

    override fun observeTotalPrice(): LiveData<Float> {
        return observableTotalPrice
     }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return if(shouldReturnNetworkError) {
            Resource.error("Error", null)
        } else {
            Resource.success(ImageResponse(listOf(), 0, 0))
        }
     }
}