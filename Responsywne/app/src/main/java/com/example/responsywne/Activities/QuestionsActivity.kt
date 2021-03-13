package com.example.responsywne.Activities

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.responsywne.*
import com.example.responsywne.Adapters.QuestionAdapter
import com.example.responsywne.Listeners.AnswerListener
import com.example.responsywne.Listeners.ListListener
import com.example.responsywne.Listeners.SuccessListener
import com.example.responsywne.Managers.DataManager
import com.example.responsywne.Managers.DatabaseManager
import com.example.responsywne.Model.Question
import com.example.responsywne.PopUpShowers.ConfirmationShower
import kotlinx.android.synthetic.main.activity_questions.*

class QuestionsActivity : AppCompatActivity() {
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)

        val ctx = this
        val testNumber = intent.extras?.get(getString(R.string.whichTest)) as Int

        //widok action bara
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar_to_test)
        val actionBarView = supportActionBar!!.customView
        val inscription = actionBarView.findViewById<TextView>(R.id.inscription);
        inscription.text = getString(R.string.test) + DataManager.listOfLessons[testNumber - 1].title
        supportActionBar!!.customView = actionBarView

        //pobranie wymiarów ekranu
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        var listOfAnswers = mutableListOf<String>("", "", "", "")
        DatabaseManager.readQuestions(
            this,
            testNumber,
            object : ListListener {
                override fun <T> onSuccess(listOfSth: MutableList<T>) {
                    DataManager.randomQuestion =
                        (listOfSth as MutableList<Question>).shuffled()
                            .toMutableList().take(4).toMutableList()
                    val questionsAdapter =
                        QuestionAdapter(
                            ctx,
                            DataManager.randomQuestion,
                            displayMetrics,
                            object :
                                AnswerListener {
                                override fun onCheckAnswer(position: Int, answer: String) {
                                    listOfAnswers.set(position, answer)
                                    Log.println(Log.ASSERT, "listaOdp", listOfAnswers.toString())
                                }

                            },
                            object :
                                SuccessListener {
                                override fun onSuccess() {
                                    if (!listOfAnswers.contains("")) {
                                        ConfirmationShower.showConfirmationPopUp(
                                            ctx,
                                            object :
                                                SuccessListener {
                                                override fun onSuccess() {
                                                    val answersActivity =
                                                        Intent(ctx, AnswersActivity::class.java)
                                                    answersActivity.putExtra(
                                                        ctx.getString(R.string.whichTest),
                                                        testNumber
                                                    )
                                                    answersActivity.putExtra(
                                                        ctx.getString(R.string.answers),
                                                        listOfAnswers as ArrayList
                                                    )
                                                    answersActivity.putExtra(
                                                        getString(R.string.myGrade),
                                                        0
                                                    )
                                                    startActivity(answersActivity)
                                                }

                                            },
                                            getString(R.string.goTest)
                                        )
                                    } else {
                                        Toast.makeText(
                                            ctx,
                                            getString(R.string.getAnswers),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                            })
                    lVAllQuestions.adapter = questionsAdapter
                }

            })
    }

    override fun onBackPressed() {
        Toast.makeText(this, getString(R.string.oneTest), Toast.LENGTH_SHORT).show()
    }
}