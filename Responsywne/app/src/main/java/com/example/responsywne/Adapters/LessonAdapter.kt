package com.example.responsywne.Adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.responsywne.Helpers.ItemLessonAdapterHelper
import com.example.responsywne.R
import kotlinx.android.synthetic.main.item_one_lesson.view.*

class LessonAdapter(private val context: Activity, private val allLessonsList: MutableList<ItemLessonAdapterHelper>):
ArrayAdapter<ItemLessonAdapterHelper> (context,  R.layout.item_one_lesson, allLessonsList){

    override fun getView(position:Int, view: View?, parent: ViewGroup): View
    {
        val inflater = context.layoutInflater
        val oneItemRow = inflater.inflate(R.layout.item_one_lesson, null, true)

        oneItemRow.tVLesson.setText(allLessonsList[position].title)
        oneItemRow.iVLesson.setImageBitmap(allLessonsList[position].photo)


        return oneItemRow
    }
}