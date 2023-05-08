package com.example.jobhunt.Service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface EmailService {
    @POST("api/mail")
    fun sendEmail(@Body email: String): Call<String>
}