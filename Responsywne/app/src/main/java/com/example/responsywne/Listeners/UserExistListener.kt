package com.example.responsywne.Listeners

import com.example.responsywne.Model.User

interface UserExistListener {
    fun onExist(user: User)
    fun onNotExist()
    fun onFailure()
}