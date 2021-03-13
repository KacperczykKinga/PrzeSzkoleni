package com.example.responsywne.Activities

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.responsywne.*
import com.example.responsywne.Adapters.LessonAdapter
import com.example.responsywne.Helpers.ItemLessonAdapterHelper
import com.example.responsywne.Listeners.ListListener
import com.example.responsywne.Managers.DataManager
import com.example.responsywne.Managers.DatabaseManager
import com.example.responsywne.Model.Lesson
import kotlinx.android.synthetic.main.action_bar_with_average.view.*
import kotlinx.android.synthetic.main.activity_lessons.*

class LessonsActivity : AppCompatActivity() {
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lessons)

        //widok action bara
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar_with_average)
        val actionBarView = supportActionBar!!.customView
        val inscription = actionBarView.findViewById<TextView>(R.id.inscription);
        inscription.text = getString(R.string.app_name)
        supportActionBar!!.customView = actionBarView

        //cofanie do strony głównej
        val bBack = actionBarView.findViewById<ImageView>(R.id.arrow)
        bBack.setOnClickListener {
            onBackPressed()
        }

        if(DataManager.listOfLessons.size == 0) {
            pBLessons.visibility = View.VISIBLE
            DatabaseManager.readAllLessons(
                this,
                object : ListListener {
                    override fun <T> onSuccess(listOfSth: MutableList<T>) {
                        (listOfSth as MutableList<Lesson>).sort()
                        DataManager.listOfLessons =
                            listOfSth
                    }
                },
                object : ListListener {
                    override fun <T> onSuccess(listOfSth: MutableList<T>) {
                        (listOfSth as MutableList<ItemLessonAdapterHelper>).sort()
                        DataManager.listOfLessonItems =
                            listOfSth
                        adapterDo(listOfSth as MutableList<ItemLessonAdapterHelper>)
                        pBLessons.visibility = View.GONE
                    }
                })
        }
        else
        {
            adapterDo(DataManager.listOfLessonItems)
        }

        if(DataManager.user.average == 0.0)
        {
           // actionBarView.average.setText(getString(R.string.emptyAverage))
        }
        else
        {
            actionBarView.average.setText(String.format(" %.2f ", DataManager.user.average))
        }

        actionBarView.toGrades.setOnClickListener {
            val gradesIntent = Intent(this, GradesActivity::class.java)
            startActivity(gradesIntent)
        }
    }

    private fun adapterDo(listOfLessons: MutableList<ItemLessonAdapterHelper>)
    {
        val lessonsAdapter =
            LessonAdapter(this, listOfLessons)
        lVAllLessons.adapter = lessonsAdapter

        lVAllLessons.setOnItemClickListener({ adapterView, view, position, id ->
            if(DataManager.listOfLessons[position].listOfTopics.size == 0)
            {
                Toast.makeText(this, getString(R.string.noTopics), Toast.LENGTH_SHORT).show()
            }
            else {
                val topicsIntent = Intent(this, TopicsActivity::class.java)
                topicsIntent.putExtra(getString(R.string.whichLesson), position)
                startActivity(topicsIntent)
            }
        }
        )
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}