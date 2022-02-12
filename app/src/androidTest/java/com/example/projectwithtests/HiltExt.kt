package com.example.projectwithtests
/**Basically we create this file to allow dragger hilt to launch fragments inside our new fake activity
 * */
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.core.util.Preconditions
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
/** * reified means that we can access the class of the generic type we created
 * * inline functions are just efficient way of implementing lambda expressions , as the lambda expressions don't
 * have any address of their own and just compiled ... inline functions just remove this issue */
@ExperimentalCoroutinesApi
inline fun <reified T:Fragment> LaunchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
    themeResId: Int = R.style.FragmentScenarioEmptyFragmentActivityTheme,
    fragmentFactory: FragmentFactory? = null,//it allows to injects the dependency for our fragments
    crossinline action: T.() -> Unit = {}//used to get reference of fragment we created
){
    //down here we create the intent to launch our activity to run fragment inside it's fragment container
    val mainActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            TestActivity::class.java
        )
    ).putExtra("androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY", themeResId)
//launching the activity from intent created above
    ActivityScenario.launch< TestActivity>(mainActivityIntent).onActivity { activity ->
        fragmentFactory?.let {
            activity.supportFragmentManager.fragmentFactory = it
        }
        val fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            Preconditions.checkNotNull(T::class.java.classLoader),
            T::class.java.name
        )
        fragment.arguments = fragmentArgs

        activity.supportFragmentManager.beginTransaction()
            .add(android.R.id.content, fragment, "")
            .commitNow()

        (fragment as T).action()
    }
}