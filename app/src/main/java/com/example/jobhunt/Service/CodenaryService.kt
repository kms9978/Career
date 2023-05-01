package com.example.jobhunt.Service

import com.example.jobhunt.dataModel.CodenaryData
import com.example.jobhunt.dataModel.CodenaryResponse
import retrofit2.Call
import retrofit2.http.GET

interface CodenaryService {
    @GET("withcareer/backend_jobhunt/main/codenary/news.json")
    fun getNews(): Call<CodenaryResponse>
}