package com.example.responsywne.Activities

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.responsywne.Listeners.SuccessListener
import com.example.responsywne.Listeners.UserExistListener
import com.example.responsywne.Managers.DatabaseManager
import com.example.responsywne.Model.User
import com.example.responsywne.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //widok action bara
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar_inscription)
        val actionBarView = supportActionBar!!.customView
        val inscription = actionBarView.findViewById<TextView>(R.id.inscription);
        inscription.text = getString(R.string.registration)
        supportActionBar!!.customView = actionBarView;

        //cofanie do strony głównej
        val bBack = actionBarView.findViewById<ImageView>(R.id.arrow)
        bBack.setOnClickListener {
            onBackPressed()
        }
    }

    fun register(v : View)
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
            DatabaseManager.usernameExist(this, username, object:
                UserExistListener
            {
                override fun onExist(user: User) {
                    Toast.makeText(ctx, getString(R.string.anotherUsername), Toast.LENGTH_SHORT).show()
                }

                override fun onNotExist() {
                    registerUser()
                }

                override fun onFailure() {
                    Toast.makeText(ctx, getString(R.string.sthBad), Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

    //rejestruje użytkownika
    private fun registerUser()
    {
        val ctx = this
        //utworzenie i zapisanie uzytkownika w bazie
        val me = User(
            eTUsername.text.toString(),
            eTPassword.text.toString()
        )
        me.writeUser(this, object:
            SuccessListener {
            override fun onSuccess() {
                Toast.makeText(ctx, getString(R.string.goodRegistration), Toast.LENGTH_SHORT).show()
                val haveAccountIntent = Intent(ctx, HaveAccountActivity::class.java);
                startActivity(haveAccountIntent);
            }
        })
    }
}