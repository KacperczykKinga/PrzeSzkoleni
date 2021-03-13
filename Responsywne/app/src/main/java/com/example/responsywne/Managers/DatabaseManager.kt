package com.example.responsywne.Managers

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import com.example.responsywne.*
import com.example.responsywne.Helpers.ContentItemAdapterHelper
import com.example.responsywne.Helpers.ItemLessonAdapterHelper
import com.example.responsywne.Listeners.BytesListener
import com.example.responsywne.Listeners.ListListener
import com.example.responsywne.Listeners.SuccessListener
import com.example.responsywne.Listeners.UserExistListener
import com.example.responsywne.Model.Content
import com.example.responsywne.Model.Lesson
import com.example.responsywne.Model.Question
import com.example.responsywne.Model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class DatabaseManager {

    companion object {

        var database = Firebase.database.reference

        fun writeUser(context: Context, username: String, user: User, listener: SuccessListener?) {
            database.child(context.getString(
                R.string.databaseUsers
            )).child(username).setValue(user)
                .addOnSuccessListener {
                    listener?.onSuccess()
                }
        }

        fun usernameExist(context: Context, username: String, listener: UserExistListener) {
            database.child(context.getString(
                R.string.databaseUsers
            )).child(username)
                .addListenerForSingleValueEvent(
                    object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                val user = snapshot.getValue(User::class.java)
                                listener.onExist(user!!)
                            } else {
                                listener.onNotExist()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            listener.onFailure()
                        }
                    }
                );
        }

        fun readAllLessons(context: Context, lessonListener: ListListener, itemLessonListener: ListListener)
        {
            database.child(context.getString(
                R.string.databaseLessons
            )).addListenerForSingleValueEvent(
                object: ValueEventListener
                {
                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(context, context.getString(R.string.sthBad), Toast.LENGTH_SHORT).show()
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists())
                        {
                            var listOfLessonItems = mutableListOf<ItemLessonAdapterHelper>()
                            var listOfLessons = mutableListOf<Lesson>()
                            for(lesson in snapshot.children)
                            {
                                val oneLesson = lesson.getValue(Lesson::class.java)
                                listOfLessons.add(oneLesson!!)
                            }
                            lessonListener.onSuccess(listOfLessons)
                            val allLessonsCount = listOfLessons.size - 1
                            for(i in 0..listOfLessons.size - 1)
                            {
                                PhotoManager.readConcretePhoto(
                                    listOfLessons[i].photo,
                                    object :
                                        BytesListener {
                                        override fun onSuccess(bytes: ByteArray) {
                                            val bmp =
                                                BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                                            listOfLessonItems.add(
                                                ItemLessonAdapterHelper(
                                                    listOfLessons[i].id,
                                                    listOfLessons[i].title,
                                                    bmp
                                                )
                                            )
                                            if (listOfLessonItems.size - 1 == allLessonsCount) {
                                                itemLessonListener.onSuccess(listOfLessonItems)
                                            }
                                        }

                                    })
                            }
                        }
                        else
                        {
                            Toast.makeText(context, context.getString(R.string.sthBad), Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            )
        }

        fun readQuestions(context: Context, testNumber: Int, contentListener: ListListener)
        {
            database.child(context.getString(
                R.string.databaseTest
            )).child(testNumber.toString()).addListenerForSingleValueEvent(
                object: ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(context, context.getString(R.string.sthBad), Toast.LENGTH_SHORT).show()
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists())
                        {
                            var listOfQuestions = mutableListOf<Question>()
                            for(content in snapshot.children)
                            {
                                val oneQuestion = content.getValue(Question::class.java) as Question
                                listOfQuestions.add(oneQuestion)
                            }
                            contentListener.onSuccess(listOfQuestions)

                        }
                        else
                        {
                            Toast.makeText(context, context.getString(R.string.sthBad), Toast.LENGTH_SHORT).show()
                        }
                    }

                })
        }

        fun readContents(context: Context, contentNumber: Int, contentListener: ListListener, contentItemListener: ListListener)
        {
            database.child(context.getString(
                R.string.databaseContents
            )).child(contentNumber.toString()).child(context.getString(R.string.databaseListContents)).addListenerForSingleValueEvent(
                object: ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(context, context.getString(R.string.sthBad), Toast.LENGTH_SHORT).show()
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists())
                        {
                            var listOfContents = mutableListOf<Content>()
                            for(content in snapshot.children)
                            {
                                val oneContent = content.getValue(Content::class.java) as Content
                                listOfContents.add(oneContent)
                            }
                            contentListener.onSuccess(listOfContents)
                            val contentsCount = listOfContents.size - 1
                            val startedCount = DataManager.listOfContentsItem.size
                            for(i in 0..contentsCount)
                            {
                                if(listOfContents[i].contentType == "photo") {
                                    PhotoManager.readConcretePhoto(listOfContents[i].content,
                                        object :
                                            BytesListener { override fun onSuccess(bytes: ByteArray) {
                                                DataManager.listOfContentsItem.add(
                                                    ContentItemAdapterHelper(
                                                        listOfContents[i].content, bytes
                                                    )
                                                )
                                                if (DataManager.listOfContentsItem.size - startedCount - 1 == contentsCount) {
                                                    contentItemListener.onSuccess(DataManager.listOfContentsItem)
                                                }
                                            }

                                        })
                                }
                                else
                                {
                                    DataManager.listOfContentsItem.add(
                                        ContentItemAdapterHelper(
                                            listOfContents[i].content,
                                            null
                                        )
                                    )
                                    if (DataManager.listOfContentsItem.size - startedCount - 1 == contentsCount) {
                                        contentItemListener.onSuccess(DataManager.listOfContentsItem)
                                    }
                                }
                            }
                        }
                        else
                        {
                            Toast.makeText(context, context.getString(R.string.sthBad), Toast.LENGTH_SHORT).show()
                        }
                    }

                })
        }
    }
}