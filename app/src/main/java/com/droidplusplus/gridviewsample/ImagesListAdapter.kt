package com.droidplusplus.gridviewsample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.droidplusplus.gridviewsample.ImagesListAdapter.MViewHolder
import com.droidplusplus.gridviewsample.databinding.LayoutImageItemBinding

class ImagesListAdapter :
    ListAdapter<ImageViewItem, MViewHolder>(MDiffUtilCallBack()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        context = parent.context
        val binding = LayoutImageItemBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return MViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MViewHolder(private val binding: LayoutImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ImageViewItem?) {
            item?.imageUrl?.takeIf { it.isNotBlank() }?.let { url ->

                // Image
                Glide.with(binding.imageView).load(url).into(binding.imageView)

                // Order
                binding.orderTextView.text = "${item.order}"

                // Selected check mark
                if (item.isSelected) {
                    binding.selectionMarkImageView.visibility = View.VISIBLE
                } else {
                    binding.selectionMarkImageView.visibility = View.GONE
                }
            }
        }
    }

    fun onItemClick(view: View?, position: Int) {
        currentList[position].isSelected = !currentList[position].isSelected
        notifyItemChanged(position)
    }

    fun onLongItemClick(view: View?, position: Int) {
        view?.setBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.teal_700
            )
        )
    }
}

private class MDiffUtilCallBack : DiffUtil.ItemCallback<ImageViewItem>() {
    override fun areItemsTheSame(oldItem: ImageViewItem, newItem: ImageViewItem) =
        oldItem.imageUrl == newItem.imageUrl

    override fun areContentsTheSame(oldItem: ImageViewItem, newItem: ImageViewItem) =
        oldItem == newItem

}