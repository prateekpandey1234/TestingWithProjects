package com.example.projectwithtests.repository


import androidx.lifecycle.LiveData
import com.example.projectwithtests.data.local.ShoppingItem
import com.example.projectwithtests.data.remote.responses.ImageResponse
/**This repository is a interface or more like a parent repository which is necessary here as we want to test
 * our repository functions which is [DefaultRepository] how to behaves with our viewModel and we don't need
 * to use main repo because it may get complex */
interface ShoppingRepository {

    /**here we just define the return type and the parameters required for our main repo [DefaultRepository] */
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): com.example.projectwithtests.other.Resource<ImageResponse>
}