package com.example.jobhunt.Service

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ChatService {
    @POST("api/chat")
    @FormUrlEncoded
    fun sendMessage(@Field("prompt") prompt: String): Call<String>
}
