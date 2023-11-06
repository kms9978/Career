package com.example.jobhunt.Utils

import android.content.Context
import android.content.SharedPreferences

class TokenManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)

    fun getToken(): String? {
        return prefs.getString("token", null)
    }
}