package com.example.jobhunt.Service

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
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

//    @GET("/jobhunting-page/jobhunt/d8d9ed7aab4fc2b6cf4a7b9f42e787912a334b75/companyInfo/news.json")
//    fun getRecentRecruitData(): Call<Map<String,>>
}