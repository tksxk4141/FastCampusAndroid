package com.example.aop_part5_chapter03.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aop_part5_chapter03.databinding.ViewholderImageBinding
import com.example.aop_part5_chapter03.extensions.loadCenterCrop

class ImageViewPagerAdapter(
    var uriList: MutableList<Uri>
) : RecyclerView.Adapter<ImageViewPagerAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ViewholderImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindData(uriList[position])
    }

    override fun getItemCount(): Int = uriList.size

    class ImageViewHolder(
        private val binding: ViewholderImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(uri: Uri) = with(binding) {
            imageView.loadCenterCrop(uri.toString())
        }

    }

}