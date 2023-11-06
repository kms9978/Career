package com.example.jobhunt.Service

import com.example.jobhunt.DataModel.RecentRecruit
import retrofit2.Call
import retrofit2.http.GET
interface RecruitService {
    @GET("withcareer/backend_jobhunt/main/companyInfo/news.json")
    fun getRecruits(): Call<Map<String, RecentRecruit>>
    // 결과로 Map<String, RecentRecruit> 타입의 데이터를 가져오기.


}