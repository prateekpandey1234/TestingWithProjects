package com.example.projectwithtests.ui


import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.projectwithtests.R
import kotlinx.android.synthetic.main.fragment_add_shopping_item.*


/**to test our fragments we need to create a fake activity not inside main directory but we have to create
 * Another directory inside the src directory named as debug
 * Steps to create the new directory
 * * switch to project view in the left tab
 * * create new directory named as debug
 * * create another directory named as java
 * * follow the same package structure as in our main package
 * * create a new activity as a test inside the package
 * */
class AddShoppingItemFragment : Fragment(R.layout.fragment_add_shopping_item) {

    lateinit var viewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]
        ivShoppingImage.setOnClickListener {
            findNavController().navigate(
                AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
            )
        }
/**the following code is a callback created for the event when user presses the back button*/
        val backcallback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                //resetting the url link so that same url is not accessed multiple times
                viewModel.setCurImageUrl(" ")
                findNavController().popBackStack()
                //Attempts to pop the controller's back stack. Analogous to when the user
                // presses the system Back button when the associated navigation host has focus
            }
        }
        //adding the callback method to the activity
        requireActivity().onBackPressedDispatcher.addCallback(backcallback)
    }
}