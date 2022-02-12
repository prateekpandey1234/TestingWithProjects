package com.example.projectwithtests

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication



/**We also have to define that we are using Custom Hilt tests down below we created in the Build.gradle file
 * inside the defaultconfig scope by giving path of this file
 * */
class HiltTestRunner: AndroidJUnitRunner() {
    /**Basically we need to initiate our database object or DAO [ShoppingDaoTest] every time we test which might not be good practice for large
    * Projects , hence we use Hilt as a alternative to test our database
    * */
/**
android.app.Instrumentation Perform instantiation of the process's Application object.
The default implementation provides the normal system behavior.*/
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}