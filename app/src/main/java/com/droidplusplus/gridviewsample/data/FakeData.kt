package com.droidplusplus.gridviewsample.data

import com.droidplusplus.gridviewsample.ImageViewItem

object FakeData {
    fun getImagesList(size: Int = 100): MutableList<ImageViewItem> {
        val imagesList = mutableListOf<ImageViewItem>()
        repeat(size) {
            imagesList.add(
                it,
                ImageViewItem(
                    imageUrl = "https://res.cloudinary.com/vahvah/image/upload/v1622782178/VahVah/User%20Profiles/development/owrysswdj9tes1pfavcv.jpg",
                    order = it + 1
                )
            )
        }

        return imagesList
    }
}