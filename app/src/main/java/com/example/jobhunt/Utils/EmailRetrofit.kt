package com.example.jobhunt.Utils

import com.example.jobhunt.Service.EmailService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EmailRetrofit {
    private const val BASE_URL = "https://knu-carrer.org"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getEmailService(): EmailService = retrofit.create(EmailService::class.java)
}