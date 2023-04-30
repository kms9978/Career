package com.example.jobhunt.Service

import com.example.jobhunt.dataModel.CodenaryData
import retrofit2.Call
import retrofit2.http.GET

interface CodenaryService {
    @GET("codenary/news.json")
    fun getNews(): Call<List<CodenaryData>>
}