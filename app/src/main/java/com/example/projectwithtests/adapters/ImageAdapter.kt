package com.example.projectwithtests.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.projectwithtests.R
import com.example.projectwithtests.data.local.ShoppingItem
import kotlinx.android.synthetic.main.item_image.view.*
import javax.inject.Inject

class ImageAdapter @Inject constructor(
    private val glide:RequestManager
): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    class ImageViewHolder (itemview: View):RecyclerView.ViewHolder(itemview)


    //using String as a callback because we want to only compare url of the images .....
    private val differlist = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    private val Data = AsyncListDiffer(this , differlist)

    var images:List<String>
    get() = Data.currentList
    set(value) = Data.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
       return ImageViewHolder(LayoutInflater.from(parent.context).inflate(
           R.layout.item_image,
           parent,
           false
       ))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url = images[position]
        holder.itemView.apply {
            glide.load(url).into(ivShoppingImage)
            setOnClickListener {
                OnItemClickListener?.let {
                    it(url)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return  images.size
    }
//setting up onClickListener
    private var OnItemClickListener : ((String) -> Unit)? = null

     fun setOnItemClickListener(listener:(String)->Unit){
        OnItemClickListener=listener
    }

}