package com.droidplusplus.gridviewsample

data class ImageViewItem(
    var imageUrl: String,
    var order: Int,
    var isSelected: Boolean = false
)