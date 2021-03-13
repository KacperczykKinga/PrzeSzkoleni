package com.example.responsywne.Activities

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.TextView
import com.example.responsywne.Adapters.AnswerAdapter
import com.example.responsywne.Managers.DataManager
import com.example.responsywne.R
import kotlinx.android.synthetic.main.action_bar_inscription.view.*
import kotlinx.android.synthetic.main.activity_answers.*

class AnswersActivity : AppCompatActivity() {
    private var testNumber: Int? = null
    private var answers: ArrayList<String>? = null
    private var grade: Int = 0

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answers)

        val ctx = this
        testNumber = intent.extras?.get(getString(R.string.whichTest)) as Int
        answers = intent.extras?.get(getString(R.string.answers)) as ArrayList<String>
        grade = intent.extras?.get(getString(R.string.myGrade)) as Int

        //widok action bara
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar_inscription)
        val actionBarView = supportActionBar!!.customView
        val inscription = actionBarView.findViewById<TextView>(R.id.inscription);
        inscription.text = getString(R.string.test) + DataManager.listOfLessons[testNumber!! - 1].title
        supportActionBar!!.customView = actionBarView;

        //pobranie wymiarów ekranu
        val displayMetrics = DisplayMetrics()
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)

        actionBarView.arrow.setOnClickListener {
            onBackPressed()
        }



                if(grade == 0) {
                    var grade = 1
                    for (i in 0..3) {
                        if (DataManager.randomQuestion[i].answer == answers!![i]) {
                            grade++
                        }
                    }
                    DataManager.user.addGrade(ctx, testNumber!! - 1, grade)
                    val answersAdapter =
                        AnswerAdapter(
                            ctx,
                            DataManager.randomQuestion,
                            answers as ArrayList,
                            grade,
                            displayMetrics
                        )
                    lVAllAnswers.adapter = answersAdapter
                }
                else
                {
                    val answersAdapter =
                        AnswerAdapter(
                            ctx,
                            DataManager.randomQuestion,
                            answers as ArrayList,
                            grade,
                            displayMetrics
                        )
                    lVAllAnswers.adapter = answersAdapter
                }


    }

    override fun onBackPressed() {
        val topicsIntent = Intent(this, TopicsActivity::class.java)
        topicsIntent.putExtra(getString(R.string.whichLesson), testNumber!! - 1)
        topicsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(topicsIntent)
    }
}