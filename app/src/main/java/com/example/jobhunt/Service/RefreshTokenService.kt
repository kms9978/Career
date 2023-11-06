package com.example.jobhunt.Service

import com.example.jobhunt.DataModel.response.LoginResponse
import com.example.jobhunt.DataModel.request.RefreshTokenRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenService {
    @POST("/api/login")
    fun refreshAccessToken(@Body request: RefreshTokenRequest): Call<LoginResponse>
}