package com.example.projectwithtests.ui


import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.projectwithtests.R
import com.example.projectwithtests.adapters.ImageAdapter
import com.example.projectwithtests.other.Status
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_shopping_item.*
import javax.inject.Inject


/**to test our fragments we need to create a fake activity not inside main directory but we have to create
 * Another directory inside the src directory named as debug
 * Steps to create the new directory
 * * switch to project view in the left tab
 * * create new directory named as debug
 * * create another directory named as java
 * * follow the same package structure as in our main package
 * * create a new activity as a test inside the package
 * */
class AddShoppingItemFragment @Inject constructor(
    private val glide:RequestManager
): Fragment(R.layout.fragment_add_shopping_item) {

    lateinit var viewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]
        subscribetochanges()
        ivShoppingImage.setOnClickListener {
            findNavController().navigate(
                AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
            )
        }
/**the following code is a callback created for the event when user presses the back button*/

        btnAddShoppingItem.setOnClickListener {
            viewModel.insertShoppingItem(
                etShoppingItemName.text.toString(),
                etShoppingItemAmount.text.toString(),
                etShoppingItemPrice.text.toString()
            )
        }
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
    private fun subscribetochanges(){
        viewModel.curImageUrl.observe(viewLifecycleOwner, Observer {
            glide.load(it).into(ivShoppingImage)
        })
        viewModel.insertShoppingItemStatus.observe(viewLifecycleOwner , Observer {
            it.getContentIfNotHandled()?.let {res ->
                when(res.status){
                    Status.SUCCESS -> {
                        Snackbar.make(
                            requireActivity().rootLayout,
                            "Shopping item Added",
                            Snackbar.LENGTH_LONG
                        ).show()
                        findNavController().popBackStack()
                    }
                    Status.LOADING->{
                        //no operation
                    }
                    Status.ERROR->{
                        Snackbar.make(
                            requireActivity().rootLayout,
                            "An Error occurred",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        })
    }
}