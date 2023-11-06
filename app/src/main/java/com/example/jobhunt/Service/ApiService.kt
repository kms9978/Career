package com.example.jobhunt.Service

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

// ApiService라는 이름의 interface 생성
interface ApiService {
    // @POST 어노테이션을 이용하여 /api/signup 경로의 POST 요청 처리
    @POST("/api/signup")

// Header에 Content-Type을 application/json으로 설정
    @Headers("Content-Type: application/json")

// RequestBody 타입의 userData 파라미터를 받고, ResponseBody 타입의 Call 객체를 반환하는 postData 메소드 생성
    fun postData(@Body userData: RequestBody): Call<ResponseBody>


    // 채용정보 읽기여기서 GET 요청을 수행하고, "recentRecruits"는 요청할 엔드포인트의 경로를 나타냄. suspend 키워드는 코루틴에서 사용될 것을 나타낸다.
//    @GET("recent_recruit")
//    suspend fun getRecentRecruit(): MutableList<RecentRecruit>
}