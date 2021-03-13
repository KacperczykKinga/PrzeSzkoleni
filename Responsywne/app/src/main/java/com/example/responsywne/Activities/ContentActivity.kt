package com.example.responsywne.Activities

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.responsywne.*
import com.example.responsywne.Adapters.ContentAdapter
import com.example.responsywne.Listeners.ListListener
import com.example.responsywne.Managers.DataManager
import com.example.responsywne.Managers.DatabaseManager
import com.example.responsywne.Model.Content
import kotlinx.android.synthetic.main.activity_content.*


class ContentActivity : AppCompatActivity() {
    private var  contentAdapter: ContentAdapter? = null
    private var contentId: Int? = null
    private var listOfPlays: MutableList<Boolean> = mutableListOf()

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        val contentName = intent.extras?.get(getString(R.string.whichTopic)) as String
        val maxTopics = intent.extras?.get(getString(R.string.macTopics)) as Int
        contentId = intent.extras?.get(getString(R.string.whichContent)) as Int

        Log.println(Log.ASSERT, "content id", contentId.toString())
        //widok action bara
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar_inscription)
        val actionBarView = supportActionBar!!.customView
        val inscription = actionBarView.findViewById<TextView>(R.id.inscription);
        inscription.text = contentName
        supportActionBar!!.customView = actionBarView;

        //cofanie do strony głównej
        val bBack = actionBarView.findViewById<ImageView>(R.id.arrow)
        bBack.setOnClickListener {
            onBackPressed()
        }

        DataManager.listOfPlayers = mutableListOf()
        var listOfContent = mutableListOf<Content>()
        var act: Activity = this
        var ctx: Context = this
        pBContent.visibility = View.VISIBLE
        DatabaseManager.readContents(
            this,
            contentId!!,
            object : ListListener {
                override fun <T> onSuccess(listOfSth: MutableList<T>) {
                    listOfContent =
                        listOfSth as MutableList<Content>
                    for (content in listOfSth) {
                        DataManager.listOfPlayers.add(null)
                        listOfPlays.add(false)
                    }
                    if(DataManager.listOfStarts.size < 1)
                    {
                       for (sth in listOfSth)
                       {
                           DataManager.listOfStarts.add(0)
                       }
                    }
                }
            },
            object : ListListener {
                override fun <T> onSuccess(listOfSth: MutableList<T>) {
                    contentAdapter =
                        ContentAdapter(
                            act,
                            listOfContent,
                            contentId!!,
                            maxTopics, DataManager.listOfStarts
                        )
                    lVAllContents.adapter = contentAdapter
                    pBContent.visibility = View.GONE
                }
            })
    }

    override fun onBackPressed() {
        Log.println(Log.ASSERT, "lista playerów", DataManager.listOfPlayers.toString())
        for(i in 0..DataManager.listOfPlayers.size - 1)
        {
            DataManager.listOfPlayers[i]?.stop()
            DataManager.listOfPlayers.set(i, null)
        }
        DataManager.listOfStarts = mutableListOf()
        Log.println(Log.ASSERT, "lista playerów", DataManager.listOfPlayers.toString())
        val topicsIntent = Intent(this, TopicsActivity::class.java)
        topicsIntent.putExtra(getString(R.string.whichLesson), contentId!!/10 - 1)
        topicsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(topicsIntent)
    }

    override fun onResume()
    {
        super.onResume()
        for(i in 0..DataManager.listOfPlayers.size - 1)
        {
            if(listOfPlays[i]) DataManager.listOfPlayers[i]?.playWhenReady = true
        }
    }

    override fun onStop()
    {
        Log.println(Log.ASSERT, "lista playerów", DataManager.listOfPlayers.toString())
        for(i in 0..DataManager.listOfPlayers.size - 1)
        {
            DataManager.listOfPlayers[i]?.playWhenReady = false
            if(DataManager.listOfPlayers[i] != null && DataManager.listOfPlayers[i]!!.isPlaying)
            {
                listOfPlays[i] = true
            }
            if(DataManager.listOfPlayers[i] != null)
            {
                DataManager.listOfStarts[i] = DataManager.listOfPlayers[i]!!.currentPosition
            }
        }
        Log.println(Log.ASSERT, "lista playerów", DataManager.listOfPlayers.toString())
        super.onStop()
    }
}