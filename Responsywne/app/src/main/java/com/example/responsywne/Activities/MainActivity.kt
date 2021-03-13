package com.example.responsywne.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.responsywne.Managers.DataManager
import com.example.responsywne.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DataManager.listOfContentsItem = mutableListOf()
        DataManager.listOfLessonItems = mutableListOf()
        DataManager.listOfLessons = mutableListOf()
    }

    fun haveAccount(view: View)
    {
        val haveAccountIntent = Intent(this, HaveAccountActivity::class.java);
        startActivity(haveAccountIntent);
    }

    fun register(view: View)
    {
        val registerIntent = Intent(this, RegisterActivity::class.java);
        startActivity(registerIntent);
    }
}