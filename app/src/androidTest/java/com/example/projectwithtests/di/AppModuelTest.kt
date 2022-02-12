package com.example.projectwithtests.di

import android.content.Context
import androidx.room.Room
import com.example.projectwithtests.data.local.ShoppingItemDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
@Module
@InstallIn(SingletonComponent::class)
object AppModuelTest {
    @Provides
    @Named("test_db")
    fun provideInMenoryDb(@ApplicationContext context:Context)=
        Room.inMemoryDatabaseBuilder(context,ShoppingItemDatabase::class.java)
            .allowMainThreadQueries()
            .build()
//.allowMainThreadQueries -> Disables the main thread query check for Room.
//                          Room ensures that Database is never accessed on the main thread
//                           because it may lock the main thread and trigger an ANR.

}