package com.example.projectwithtests.ui

import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.projectwithtests.R
import com.example.projectwithtests.adapters.ImageAdapter
import com.example.projectwithtests.other.Constants.GRID_SPAN_COUNT
import com.example.projectwithtests.other.Status
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_image_pick.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImagePickFragment @Inject constructor(
     val imageAdapter: ImageAdapter
) : Fragment(R.layout.fragment_image_pick) {
    lateinit var viewModel: ShoppingViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        subscribeToObservers()
        setupRecyclerView()
        imageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setCurImageUrl(it)
        }
        var job: Job? = null
        etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = lifecycleScope.launch {
                delay(400)
                editable?.let {
                    if(editable.toString().isNotEmpty()) {
                        viewModel.searchForImage(editable.toString())
                    }
                }
            }
        }

        imageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setCurImageUrl(it)
        }
    }
    private fun subscribeToObservers() {
        viewModel.images.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let { result ->
                when(result.status) {
                    Status.SUCCESS -> {
                        val urls = result.data?.hits?.map { imageResult ->  imageResult.previewURL }
                        imageAdapter.images = urls ?: listOf()
                        progressBar.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        Snackbar.make(
                            requireActivity().rootLayout,
                            result.message ?: "An unknown error occured.",
                            Snackbar.LENGTH_LONG
                        ).show()
                        progressBar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }
    private fun setupRecyclerView(){
        rvImages.apply {
            adapter = imageAdapter
            // context – Current context, will be used to access resources.
            // spanCount – The number of columns in the grid
            layoutManager = GridLayoutManager(requireContext(),GRID_SPAN_COUNT)

        }
    }
}