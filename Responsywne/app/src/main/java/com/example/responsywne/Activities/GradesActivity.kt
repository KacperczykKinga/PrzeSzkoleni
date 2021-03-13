package com.example.responsywne.Activities

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.ImageView
import android.widget.TextView
import com.example.responsywne.*
import com.example.responsywne.Adapters.GradeAdapter
import com.example.responsywne.Listeners.SuccessListener
import com.example.responsywne.Managers.DataManager
import com.example.responsywne.PopUpShowers.ConfirmationShower
import kotlinx.android.synthetic.main.activity_grades.*

class GradesActivity : AppCompatActivity() {
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grades)

        //pobranie wymiarów ekranu
        val displayMetrics = DisplayMetrics()
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar_inscription)
        val actionBarView = supportActionBar!!.customView
        val inscription = actionBarView.findViewById<TextView>(R.id.inscription);
        inscription.text = getString(R.string.myGrades)
        supportActionBar!!.customView = actionBarView;

        //cofanie do strony głównej
        val bBack = actionBarView.findViewById<ImageView>(R.id.arrow)
        bBack.setOnClickListener {
            onBackPressed()
        }

        val gradesAdapter = GradeAdapter(
            this,
            DataManager.listOfLessonItems,
            displayMetrics
        )
        lVAllGrades.adapter = gradesAdapter


        val ctx = this
        tVAverage.setText(getString(R.string.average) + String.format(" %.2f ", DataManager.user.average))
        logOutButton.setOnClickListener {
            ConfirmationShower.showConfirmationPopUp(
                ctx,
                object : SuccessListener {
                    override fun onSuccess() {
                        val mainIntent = Intent(
                            ctx,
                            MainActivity::class.java
                        )
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(mainIntent);
                    }

                },
                getString(R.string.logOutSure)
            )

        }
    }
}