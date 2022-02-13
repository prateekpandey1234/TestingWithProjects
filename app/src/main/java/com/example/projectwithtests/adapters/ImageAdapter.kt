package com.example.projectwithtests.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class ImageAdapter @Inject constructor(
    private val glide:RequestManager
): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    class ImageViewHolder (itemview: View):RecyclerView.ViewHolder(itemview)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}