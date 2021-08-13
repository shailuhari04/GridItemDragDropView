package com.droidplusplus.gridviewsample

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewItemTouchListener: RecyclerView.OnItemTouchListener {
    override fun onInterceptTouchEvent(recyclerView: RecyclerView, e: MotionEvent): Boolean {
        MainActivity.isLongPress = e.action == MotionEvent.ACTION_MOVE
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        if (e.action == MotionEvent.ACTION_MOVE) {
            MainActivity.isLongPress = true
        }
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
}