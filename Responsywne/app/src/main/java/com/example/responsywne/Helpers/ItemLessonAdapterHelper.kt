package com.example.responsywne.Helpers

import android.graphics.Bitmap

class ItemLessonAdapterHelper(
    var id: Int = 0,
    var title: String = "",
    var photo: Bitmap? = null
): Comparable<ItemLessonAdapterHelper> {
    override fun compareTo(other: ItemLessonAdapterHelper): Int {
        return this.id.compareTo(other.id)
    }

}