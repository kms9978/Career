package com.example.jobhunt.Service

import com.example.jobhunt.dataModel.CodenaryData
import com.example.jobhunt.dataModel.CodenaryResponse
import retrofit2.Call
import retrofit2.http.GET

interface CodenaryService {
    @GET("kms9978/kms9978/main/news.json")
    fun getNews(): Call<Map<String, CodenaryData>>
}