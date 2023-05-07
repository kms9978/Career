package com.example.jobhunt.Fragment

import com.example.jobhunt.Service.BookMarkService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val retrofit: Retrofit by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(
                        "Authorization",
                        "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiYWIiLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjgzNDUxODM5fQ.gth72nIhqiqeSM6FfhYT64WtBqampa87OcxmCsxL6phPPxxn_DdX6IHqghI5VGKX3F0Fp2LX4uSN2XdmNv1gpA"
                    )
                    .build()
                chain.proceed(request)
            }
            .build()

        Retrofit.Builder()
            .baseUrl("http://54.227.205.92:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val retrofitService: BookMarkService by lazy {
        retrofit.create(BookMarkService::class.java)
    }
}