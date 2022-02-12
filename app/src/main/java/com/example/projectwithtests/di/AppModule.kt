package com.example.projectwithtests.di

import android.content.Context
import androidx.room.Room
import com.example.projectwithtests.data.local.ShoppingDao
import com.example.projectwithtests.data.local.ShoppingItemDatabase
import com.example.projectwithtests.data.remote.PixabayAPI
import com.example.projectwithtests.other.Constants.BASE_URL
import com.example.projectwithtests.other.Constants.DATABASE_NAME
import com.example.projectwithtests.repository.DefaultRepository
import com.example.projectwithtests.repository.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ShoppingItemDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()

    @Singleton
    @Provides
    fun providePixabayApi(): PixabayAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao: ShoppingDao,
        api: PixabayAPI
    ) = DefaultRepository(dao, api) as ShoppingRepository
}
