package com.example.responsywne.Adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.responsywne.R
import kotlinx.android.synthetic.main.item_one_lesson.view.*
import kotlinx.android.synthetic.main.item_one_topic.view.*

class TopicAdapter(private val context: Activity, private val allTopicsList: MutableList<String>):
    ArrayAdapter<String>(context, R.layout.item_one_topic, allTopicsList){

    override fun getView(position:Int, view: View?, parent: ViewGroup): View
    {
        val inflater = context.layoutInflater
        val oneItemRow = inflater.inflate(R.layout.item_one_topic, null, true)

        oneItemRow.tVTopicNumber.setText((position + 1).toString())
        oneItemRow.tVTopic.setText(allTopicsList[position])

        return oneItemRow
    }
}