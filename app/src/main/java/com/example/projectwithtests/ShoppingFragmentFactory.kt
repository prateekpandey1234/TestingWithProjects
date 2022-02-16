package com.example.projectwithtests

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.projectwithtests.adapters.ImageAdapter
import com.example.projectwithtests.adapters.ShoppingItemAdapter
import com.example.projectwithtests.ui.AddShoppingItemFragment
import com.example.projectwithtests.ui.ImagePickFragment
import com.example.projectwithtests.ui.ShoppingFragment
import javax.inject.Inject
/** * Traditionally, a Fragment instance could only be instantiated using its default empty constructor.
 * This is because the system would need to reinitialize it under certain circumstances like configuration changes
 * and the app’s process recreation. If it weren’t for the default constructor restriction, the system wouldn’t
 * know how to reinitialize the Fragment instance.
 * * FragmentFactory was created to work around this limitation. It helps the system create a Fragment instance
 * by providing it with the necessary arguments/dependencies needed to instantiate the Fragment.*/
class ShoppingFragmentFactory @Inject constructor(
    private val imageadapter: ImageAdapter,
    private val shoppingItemAdapter: ShoppingItemAdapter,
    val  glide:RequestManager
) : FragmentFactory() {
/**so what we do here is that we need to inject a constructor in our fragment for image adapter but by
 * Default we can not , so we use [FragmentFactory] instead as a way to pass our custom constructor to the fragment
 *  * Here we just pass the parameters for our image adapter only for [ImagePickFragment] not for every fragment */
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ImagePickFragment::class.java.name->ImagePickFragment(imageadapter)
            AddShoppingItemFragment::class.java.name->AddShoppingItemFragment(glide)
            ShoppingFragment::class.java.name->ShoppingFragment(shoppingItemAdapter)
            else->super.instantiate(classLoader, className)
        }
     }
}