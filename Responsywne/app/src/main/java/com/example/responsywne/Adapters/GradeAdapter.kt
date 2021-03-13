package com.example.responsywne.Adapters

import android.app.Activity
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.responsywne.Managers.DataManager
import com.example.responsywne.Helpers.ItemLessonAdapterHelper
import com.example.responsywne.R
import kotlinx.android.synthetic.main.item_one_grade.view.*


class GradeAdapter (private val context: Activity, private val allLessonsList: MutableList<ItemLessonAdapterHelper>, private val displayMetrics: DisplayMetrics):
    ArrayAdapter<ItemLessonAdapterHelper>(context,
        R.layout.item_one_grade, allLessonsList){

    override fun isEnabled(position: Int): Boolean {
        return false
    }

    override fun getView(position:Int, view: View?, parent: ViewGroup): View
    {
        val inflater = context.layoutInflater
        val oneItemRow = inflater.inflate(R.layout.item_one_grade, null, true)

        oneItemRow.iVIcon.setImageBitmap(allLessonsList[position].photo)
        oneItemRow.tVName.setText(allLessonsList[position].title)
        oneItemRow.tVName.width = (displayMetrics.widthPixels * 0.75).toInt()
        if(DataManager.user.lessons.contains(position))
        {
            oneItemRow.tVGrade.setText(DataManager.user.grades[position].toString())
        }

        return oneItemRow
    }
}