package com.example.responsywne.Activities

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.responsywne.PopUpShowers.ConfirmationShower
import com.example.responsywne.Listeners.SuccessListener
import com.example.responsywne.Managers.DataManager
import com.example.responsywne.R
import com.example.responsywne.Adapters.TopicAdapter
import kotlinx.android.synthetic.main.activity_topics.*

class TopicsActivity : AppCompatActivity() {
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topics)

        val whichLesson = intent.extras?.get(getString(R.string.whichLesson)) as Int
        val topicsToShow = DataManager.listOfLessons[whichLesson].listOfTopics

        //widok action bara
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar_inscription)
        val actionBarView = supportActionBar!!.customView
        val inscription = actionBarView.findViewById<TextView>(R.id.inscription);
        inscription.text = DataManager.listOfLessons[whichLesson].title
        supportActionBar!!.customView = actionBarView;

        val topicsAdapter =
            TopicAdapter(this, topicsToShow)
        lVAllTopics.adapter = topicsAdapter

        val ctx = this

        lVAllTopics.setOnItemClickListener({ adapterView, view, position, id ->
            if(position < topicsToShow.size - 1) {
                val contentIntent = Intent(this, ContentActivity::class.java)
                contentIntent.putExtra(getString(R.string.whichContent), (whichLesson + 1) * 10 + position)
                contentIntent.putExtra(getString(R.string.macTopics), (whichLesson + 1) * 10 + topicsToShow.size - 1)
                contentIntent.putExtra(getString(R.string.whichTopic), topicsToShow[position])
                startActivity(contentIntent)
            }
            else
            {
                if(DataManager.user.lessons.contains(whichLesson))
                {
                    ConfirmationShower.showConfirmationPopUp(
                        this,
                        object :
                            SuccessListener {
                            override fun onSuccess() {
                                val questionsIntent = Intent(ctx, QuestionsActivity::class.java)
                                questionsIntent.putExtra(
                                    ctx.getString(R.string.whichTest),
                                    whichLesson + 1
                                )
                                ctx.startActivity(questionsIntent)
                            }

                        },
                        ctx.getString(R.string.repeatTest)
                    )
                }
                else {
                    ConfirmationShower.showConfirmationPopUp(
                        this,
                        object :
                            SuccessListener {
                            override fun onSuccess() {
                                val questionsIntent = Intent(ctx, QuestionsActivity::class.java)
                                questionsIntent.putExtra(
                                    ctx.getString(R.string.whichTest),
                                    whichLesson + 1
                                )
                                ctx.startActivity(questionsIntent)
                            }

                        },
                        ctx.getString(R.string.goTest)
                    )
                }
            }
        })

        //cofanie do strony głównej
        val bBack = actionBarView.findViewById<ImageView>(R.id.arrow)
        bBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        val lessonsIntent = Intent(this, LessonsActivity::class.java)
        lessonsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(lessonsIntent)
    }
}