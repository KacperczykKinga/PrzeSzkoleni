package com.example.responsywne.Activities

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.responsywne.*
import com.example.responsywne.Listeners.UserExistListener
import com.example.responsywne.Managers.DataManager
import com.example.responsywne.Managers.DatabaseManager
import com.example.responsywne.Model.User
import kotlinx.android.synthetic.main.activity_have_account.*


class HaveAccountActivity : AppCompatActivity() {
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_have_account)

        //widok action bara
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar_inscription)
        val actionBarView = supportActionBar!!.customView
        val inscription = actionBarView.findViewById<TextView>(R.id.inscription);
        inscription.text = getString(R.string.logging)
        supportActionBar!!.customView = actionBarView;

        //cofanie do strony głównej
        val bBack = actionBarView.findViewById<ImageView>(R.id.arrow)
        bBack.setOnClickListener {
            onBackPressed()
        }
    }

    fun logIn(v: View)
    {
        val ctx = this
        val username = eTUsername.text.toString()
        val password = eTPassword.text.toString()

        if(username.isEmpty() || password.isEmpty())
        {
            Toast.makeText(this, getString(R.string.addData), Toast.LENGTH_SHORT).show()
        }
        else
        {
            DatabaseManager.usernameExist(
                this,
                username,
                object : UserExistListener {
                    override fun onExist(user: User) {
                        //sprawdza czy hasło pasuje do konta o danej nazwie użytkownika

                        if (user.password.equals(password)) {
                            DataManager.user = user
                            val lessonsIntent = Intent(
                                ctx,
                                LessonsActivity::class.java
                            );
                            startActivity(lessonsIntent);
                        } else {
                            Toast.makeText(
                                ctx,
                                getString(R.string.wrongPass),
                                Toast.LENGTH_SHORT
                            ).show()
                            eTPassword.setText("")
                        }

                    }

                    override fun onNotExist() {
                        Toast.makeText(
                            ctx,
                            getString(R.string.noUser),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onFailure() {
                        Toast.makeText(
                            ctx,
                            getString(R.string.sthBad),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
        }
    }

    override fun onBackPressed() {
        val mainIntent = Intent(this, MainActivity::class.java);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(mainIntent);
    }
}