package com.example.jobhunt.Service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EmailRetrofit {
    private const val BASE_URL = "http://54.227.205.92:8080/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getEmailService(): EmailService = retrofit.create(EmailService::class.java)
}