package com.example.responsywne.Adapters

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.responsywne.Activities.ContentActivity
import com.example.responsywne.Activities.QuestionsActivity
import com.example.responsywne.Helpers.ContentItemAdapterHelper
import com.example.responsywne.Listeners.SuccessListener
import com.example.responsywne.Managers.DataManager
import com.example.responsywne.Model.Content
import com.example.responsywne.PopUpShowers.ConfirmationShower
import com.example.responsywne.R
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.android.synthetic.main.item_one_content.view.*


//import sun.audio.AudioPlayer.player


class ContentAdapter (private val context: Activity, private val contentList: MutableList<Content>, private val whichLesson: Int, private val maxLessons: Int, private val listOfStarts: MutableList<Long>):
    ArrayAdapter<Content>(context,
        R.layout.item_one_content, contentList){

    override fun getCount(): Int {
        return contentList.size
    }

    override fun getItem(position: Int): Content {
        return contentList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    fun getNewElement(position: Int): View
    {
        val vh: ViewHolder? = ViewHolder()
        val inflater = context.layoutInflater
        val convertView = inflater.inflate(R.layout.item_one_content, null, true)

        val content =
            DataManager.listOfContentsItem.find { it.contentName == contentList[position].content } as ContentItemAdapterHelper
        convertView.tVTitle.setText(contentList[position].title)
        convertView.tVContent.setText(contentList[position].text)
        vh!!.title?.setText(contentList[position].title)
        convertView.setTag( vh)

        if (contentList[position].contentType.equals("photo")) {
            val bmp = BitmapFactory.decodeByteArray(
                content.contentBytes,
                0,
                content.contentBytes!!.size
            )
            convertView.photo.visibility = View.VISIBLE
            convertView.photo.setImageBitmap(bmp)
        } else if (contentList[position].contentType.equals("video")) {
            convertView.video.visibility = View.VISIBLE
            if (DataManager.listOfPlayers[position] == null) {
                val player = SimpleExoPlayer.Builder(context).build()
                convertView.video.player = player

                /*  val filmToShow = MediaItem.fromUri(Uri.parse(content.contentName))
        player.setMediaItem(filmToShow)*/

                val filmPath =
                    "android.resource://" + context.getPackageName()
                        .toString() + "/" + this.context.resources.getIdentifier(
                        content.contentName,
                        "raw",
                        this.context.packageName
                    )
                val filmUri = Uri.parse(filmPath)
                val filmToShow = MediaItem.fromUri(filmUri)
                player.setMediaItem(filmToShow)

                player.setPlayWhenReady(false);
                if(listOfStarts[position] != 0L)
                {
                    player.seekTo(listOfStarts[position])
                }
                else {
                player.seekTo(0, 0)
                }

                player.prepare();


                DataManager.listOfPlayers.set(position, player)
            }

        } else if (contentList[position].contentType.equals("music")) {
            convertView.music.visibility = View.VISIBLE
            val player = SimpleExoPlayer.Builder(context).build()
            convertView.music.player = player

            /*val musicToPlay = MediaItem.fromUri(Uri.parse(contentList[position].content))
                 player.setMediaItem(musicToPlay)*/

            val musicPath =
                "android.resource://" + context.getPackageName()
                    .toString() + "/" + this.context.resources.getIdentifier(
                    content.contentName,
                    "raw",
                    this.context.packageName
                )
            val musicUri = Uri.parse(musicPath)
            val musicToPlay = MediaItem.fromUri(musicUri)
            player.setMediaItem(musicToPlay)

            player.setPlayWhenReady(false);
            if(listOfStarts[position] != 0L)
            {
                player.seekTo(listOfStarts[position])
            }
            else {
                player.seekTo(0, 0)
            }

            player.prepare();

            DataManager.listOfPlayers.set(position, player)
        }

        convertView.lLShowHide.setOnClickListener {
            if (convertView.tVContent.maxLines == 3) {
                TransitionManager.beginDelayedTransition(convertView.cVCenter, AutoTransition())
                convertView.tVContent.maxLines = Integer.MAX_VALUE
                convertView.iVShowText.setImageDrawable(context.getDrawable(R.drawable.arrow_up))
                convertView.tVShowHide.setText(context.getString(R.string.hide))
            } else {
                TransitionManager.beginDelayedTransition(convertView.cVCenter, AutoTransition())
                convertView.tVContent.maxLines = 3
                convertView.iVShowText.setImageDrawable(context.getDrawable(R.drawable.arrow_down))
                convertView.tVShowHide.setText(context.getString(R.string.show))
            }
        }

        if (position == contentList.size - 1) {
            convertView.lastButtons.visibility = View.VISIBLE
            if (whichLesson != maxLessons - 1) {
                convertView.nextTopic.visibility = View.VISIBLE
                convertView.nextTopic.setOnClickListener {
                    for (i in 0..DataManager.listOfPlayers.size - 1) {
                        DataManager.listOfPlayers[i]?.stop()
                        DataManager.listOfPlayers.set(i, null)
                    }
                    DataManager.listOfStarts = mutableListOf()
                    val contentIntent = Intent(context, ContentActivity::class.java)
                    contentIntent.putExtra(
                        context.getString(R.string.whichContent),
                        whichLesson + 1
                    )
                    contentIntent.putExtra(context.getString(R.string.macTopics), maxLessons)
                    contentIntent.putExtra(
                        context.getString(R.string.whichTopic),
                        DataManager.listOfLessons[whichLesson / 10 - 1].listOfTopics[(whichLesson + 1) % 10]
                    )
                    context.startActivity(contentIntent)
                }
            }
            if (whichLesson % 10 != 0) {
                convertView.previousTopic.visibility = View.VISIBLE
                convertView.previousTopic.setOnClickListener {
                    for (i in 0..DataManager.listOfPlayers.size - 1) {
                        DataManager.listOfPlayers[i]?.stop()
                        DataManager.listOfPlayers.set(i, null)
                    }
                    DataManager.listOfStarts = mutableListOf()
                    val contentIntent = Intent(context, ContentActivity::class.java)
                    contentIntent.putExtra(
                        context.getString(R.string.whichContent),
                        whichLesson - 1
                    )
                    contentIntent.putExtra(context.getString(R.string.macTopics), maxLessons)
                    contentIntent.putExtra(
                        context.getString(R.string.whichTopic),
                        DataManager.listOfLessons[whichLesson / 10 - 1].listOfTopics[(whichLesson - 1) % 10]
                    )
                    context.startActivity(contentIntent)
                }
            }
            convertView.testButton.visibility = View.VISIBLE
            convertView.testButton.setOnClickListener {
                for (i in 0..DataManager.listOfPlayers.size - 1) {
                    DataManager.listOfPlayers[i]?.stop()
                    DataManager.listOfPlayers.set(i, null)
                }
                val lesson = whichLesson / 10 - 1
                if (DataManager.user.lessons.contains(lesson)) {
                    ConfirmationShower.showConfirmationPopUp(
                        context,
                        object :
                            SuccessListener {
                            override fun onSuccess() {
                                val questionsIntent = Intent(
                                    context,
                                    QuestionsActivity::class.java
                                )
                                questionsIntent.putExtra(
                                    context.getString(R.string.whichTest),
                                    whichLesson / 10
                                )
                                context.startActivity(questionsIntent)
                            }

                        },
                        context.getString(R.string.repeatTest)
                    )
                } else {
                    ConfirmationShower.showConfirmationPopUp(
                        context,
                        object :
                            SuccessListener {
                            override fun onSuccess() {
                                val questionsIntent = Intent(
                                    context,
                                    QuestionsActivity::class.java
                                )
                                questionsIntent.putExtra(
                                    context.getString(R.string.whichTest),
                                    whichLesson / 10
                                )
                                context.startActivity(questionsIntent)
                            }

                        },
                        context.getString(R.string.goTest)
                    )
                }
            }
        }
        return convertView
    }

    override fun getView(position:Int, view: View?, parent: ViewGroup): View
    {

        var thisView = view

        if(thisView != null)
        {
            val tag = thisView.getTag() as ViewHolder
            if(tag.title?.text.toString() == contentList[position].title) {

                return thisView!!
            }
            else
            {
                return getNewElement(position)
            }
        }
        else {
            return getNewElement(position)
        }
    }

    internal class ViewHolder {
        var title: TextView? = null
    }
}