package com.example.jobhunt.Fragment

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class TokenManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit {
            putString("token", token)
        }
    }

    fun getToken(): String? {
        return prefs.getString("token", null)
    }
}