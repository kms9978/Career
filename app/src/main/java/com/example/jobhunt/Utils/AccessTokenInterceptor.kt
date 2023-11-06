package com.example.jobhunt.Utils

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // SharedPreferences에서 access token을 가져옴
        val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
        val accessToken = prefs.getString("token", null)

        // access token이 있으면 Authorization 헤더를 추가하여 request를 보냄
        return if (!accessToken.isNullOrBlank()) {
            val authorizedRequest = request.newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()
            chain.proceed(authorizedRequest)
        } else {
            chain.proceed(request)
        }
    }
}