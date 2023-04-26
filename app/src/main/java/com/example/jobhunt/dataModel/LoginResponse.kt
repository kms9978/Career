package com.example.jobhunt.dataModel

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token")
    val token: String,
    @SerializedName("refresh_token")
    val refreshToken: String
)
