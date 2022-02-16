package com.example.projectwithtests.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projectwithtests.R
import com.example.projectwithtests.ShoppingFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
//adding fragmentfactory to all fragments
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(R.layout.activity_main)
    }
}