package com.example.jobhunt.Service

import com.example.jobhunt.dataModel.LoginRequest
import com.example.jobhunt.dataModel.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/api/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}