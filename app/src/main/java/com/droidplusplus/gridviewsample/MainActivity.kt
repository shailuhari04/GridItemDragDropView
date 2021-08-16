package com.droidplusplus.gridviewsample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.droidplusplus.gridviewsample.data.FakeData
import com.droidplusplus.gridviewsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), RecyclerViewOnItemTouchListener.RecyclerItemClickListener {

    private lateinit var binding: ActivityMainBinding

    var imagesList = mutableListOf<ImageViewItem>()

    private lateinit var adapter: ImagesListAdapter

    companion object {
        var isLongPress = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        adapter = ImagesListAdapter()

        imagesList = FakeData.getImagesList(500)

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

            override fun onSelectedChanged(
                viewHolder: RecyclerView.ViewHolder?,
                actionState: Int
            ) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    adapter.onSelectedChanged(viewHolder)
                }
                super.onSelectedChanged(viewHolder, actionState)
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                adapter.onClearView(viewHolder)
                super.clearView(recyclerView, viewHolder)
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        binding.recyclerView.addOnItemTouchListener(
            RecyclerViewOnItemTouchListener(
                this,
                binding.recyclerView,
                this
            )
        )
    }

    override fun onItemClick(view: View?, position: Int) {
        isLongPress = false
        adapter.onItemClick(view, position)
    }

    override fun onLongItemClick(view: View?, position: Int) {
        isLongPress = true
        adapter.onLongItemClick(view, position)
    }
}