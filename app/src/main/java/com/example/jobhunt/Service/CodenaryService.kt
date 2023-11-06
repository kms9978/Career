package com.example.jobhunt.Service

import com.example.jobhunt.DataModel.CodenaryData
import retrofit2.Call
import retrofit2.http.GET

interface CodenaryService {
    @GET("withcareer/backend_jobhunt/main/codenary/news2.json")
    fun getNews(): Call<Map<String, CodenaryData>>
    // 결과로 Map<String, CodenaryData> 타입의 데이터를 가져오기.
}