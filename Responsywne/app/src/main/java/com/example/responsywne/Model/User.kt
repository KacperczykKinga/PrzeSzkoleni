package com.example.responsywne.Model

import android.content.Context
import com.example.responsywne.Managers.DatabaseManager
import com.example.responsywne.Listeners.SuccessListener

class User(
    var username: String = "",
    var password: String = "",
    var grades: MutableList<Int> = mutableListOf(),
    var lessons: MutableList<Int> = mutableListOf(),
    var average: Double = 0.0
){
    //zapisuje usera do bazy danych
    fun writeUser(context: Context, listener: SuccessListener)
    {
        DatabaseManager.writeUser(context, username, this, listener)
    }

    fun addGrade(context: Context, lesson: Int, grade: Int)
    {
        if(lessons.contains(lesson))
        {
            grades.set(lessons.indexOf(lesson), grade)
        }
        else{
            grades.add(grade)
            lessons.add(lesson)
        }
        var sum = 0
        for(grade in grades)
        {
            sum += grade
        }
        average = sum.toDouble()/grades.size.toDouble()
        DatabaseManager.writeUser(context, username, this, null)
    }
}