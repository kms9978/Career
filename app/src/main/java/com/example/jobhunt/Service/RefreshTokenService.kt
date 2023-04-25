package com.example.jobhunt.Service

import com.example.jobhunt.dataModel.LoginResponse
import com.example.jobhunt.dataModel.RefreshTokenRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenService {
    @POST("/api/login")
    fun refreshAccessToken(@Body request: RefreshTokenRequest): Call<LoginResponse>
}