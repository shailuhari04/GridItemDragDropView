package com.droidplusplus.gridviewsample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.droidplusplus.gridviewsample.ImagesListAdapter.MViewHolder
import com.droidplusplus.gridviewsample.databinding.LayoutImageItemBinding

class ImagesListAdapter :
    ListAdapter<ImageViewItem, MViewHolder>(MDiffUtilCallBack()) {

    val TAG  = "ImagesListAdapter"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        val binding = LayoutImageItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        return MViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MViewHolder(private val binding: LayoutImageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ImageViewItem?) {

            item?.imageUrl?.takeIf { it.isNotBlank() }?.let { url ->
                Glide.with(binding.imageView).load(url).into(binding.imageView)
                binding.orderTextView.text = "${item.order}"
            }
        }
    }
}

private class MDiffUtilCallBack : DiffUtil.ItemCallback<ImageViewItem>() {
    override fun areItemsTheSame(oldItem: ImageViewItem, newItem: ImageViewItem) =
        oldItem.imageUrl == newItem.imageUrl

    override fun areContentsTheSame(oldItem: ImageViewItem, newItem: ImageViewItem) =
        oldItem == newItem

}