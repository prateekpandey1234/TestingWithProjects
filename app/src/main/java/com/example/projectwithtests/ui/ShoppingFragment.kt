package com.example.projectwithtests.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectwithtests.R
import com.example.projectwithtests.adapters.ImageAdapter
import com.example.projectwithtests.adapters.ShoppingItemAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_shopping.*
import javax.inject.Inject


class ShoppingFragment @Inject constructor(
    private val adapter2 :ShoppingItemAdapter
): Fragment(R.layout.fragment_shopping) {

    lateinit var viewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        fabAddShoppingItem.setOnClickListener {
            findNavController().navigate(
                ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment()
            )
        }
        subscribeToObservers()
        setupRecyclerView()

    }
//callback when some swiping is done on the view
    private val ItemtouchCallback = object : ItemTouchHelper.SimpleCallback(
        0,LEFT or RIGHT
    ){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.layoutPosition
            val item = adapter2.shoppingItems[pos]
            viewModel.deleteShoppingItem(item)
            Snackbar.make(requireView(), "Successfully deleted item", Snackbar.LENGTH_LONG).apply {
                setAction("Undo") {
                    viewModel.insertShoppingItemIntoDb(item)
                }
                show()
            }
        }

    }
    private fun subscribeToObservers() {
        viewModel.shoppingItems.observe(viewLifecycleOwner, Observer {
            adapter2.shoppingItems = it
        })
        viewModel.totalPrice.observe(viewLifecycleOwner, Observer {
            val price = it ?: 0f
            val priceText = "Total Price: $price€"
            tvShoppingItemPrice.text = priceText
        })
    }
    private fun setupRecyclerView() {
        rvShoppingItems.apply {
            adapter = adapter2
            layoutManager = LinearLayoutManager(requireContext())
            ItemTouchHelper(ItemtouchCallback).attachToRecyclerView(this)
        }
    }
}