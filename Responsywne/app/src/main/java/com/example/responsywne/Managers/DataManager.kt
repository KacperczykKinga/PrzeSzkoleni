package com.example.responsywne.Managers

import com.example.responsywne.Helpers.ContentItemAdapterHelper
import com.example.responsywne.Helpers.ItemLessonAdapterHelper
import com.example.responsywne.Model.Lesson
import com.example.responsywne.Model.Question
import com.example.responsywne.Model.User
import com.google.android.exoplayer2.SimpleExoPlayer

object DataManager {
    var listOfLessons: MutableList<Lesson> = mutableListOf()
    var listOfLessonItems: MutableList<ItemLessonAdapterHelper> = mutableListOf()
    var user: User =
        User()
    var listOfContentsItem: MutableList<ContentItemAdapterHelper> = mutableListOf()
    var listOfPlayers: MutableList<SimpleExoPlayer?> = mutableListOf()
    var randomQuestion: MutableList<Question> = mutableListOf()
    var listOfStarts: MutableList<Long> = mutableListOf()
}