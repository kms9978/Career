package com.example.jobhunt.Settings

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class TokenManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)

    fun getToken(): String? {
        return prefs.getString("token", null)
    }
}