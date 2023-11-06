package com.example.jobhunt.DataModel.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token")
    val token: String,
    @SerializedName("refresh_token") // Json 객체의 refresh_token 필드와 매핑
    val refreshToken: String
)