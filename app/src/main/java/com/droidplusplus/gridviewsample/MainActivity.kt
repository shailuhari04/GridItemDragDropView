package com.droidplusplus.gridviewsample

import android.content.Context
import android.os.Bundle
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.droidplusplus.gridviewsample.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var imagesList = mutableListOf<ImageViewItem>()

    lateinit var adapter: ImagesListAdapter

    companion object {
        var isLongPress = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        adapter = ImagesListAdapter()

        repeat(500) {
            imagesList.add(
                it,
                ImageViewItem(
                    imageUrl = "https://res.cloudinary.com/vahvah/image/upload/v1622782178/VahVah/User%20Profiles/development/owrysswdj9tes1pfavcv.jpg",
                    order = it + 1
                )
            )
        }

        binding.recyclerView.adapter = adapter
        adapter.submitList(imagesList)

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun isLongPressDragEnabled() = isLongPress

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags =
                    ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                return makeMovementFlags(dragFlags, 0)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                if (viewHolder.itemViewType != target.itemViewType)
                    return false
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition
                val item = imagesList.removeAt(fromPosition)
                imagesList.add(toPosition, item)
                recyclerView.adapter?.notifyItemMoved(fromPosition, toPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                imagesList.removeAt(position)
                binding.recyclerView.adapter?.notifyItemRemoved(position)
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

//        binding.recyclerView.addOnItemTouchListener(RecyclerViewItemTouchListener())

        binding.recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(
                this,
                binding.recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        isLongPress = false
                    }

                    override fun onItemLongClick(view: View?, position: Int) {
                        isLongPress = true
                    }
                })
        )

        binding.saveButton.setOnClickListener {
            adapter.currentList.forEachIndexed { index, imageViewItem ->
                println("Item -> $index with ${imageViewItem.order}")
            }
        }
    }
}