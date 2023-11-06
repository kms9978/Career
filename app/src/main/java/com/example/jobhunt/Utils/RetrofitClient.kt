package com.example.jobhunt.Utils

import android.content.Context
import com.example.jobhunt.Service.BookMarkService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient(context: Context) {
    private val tokenManager: TokenManager = TokenManager(context)

    private val retrofit: Retrofit by lazy {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(
                        "Authorization",
                        "Bearer ${tokenManager.getToken()}"
                    )
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(loggingInterceptor) // logging interceptor 추가
            .build()

        Retrofit.Builder()
            .baseUrl("https://knu-carrer.org")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val retrofitService: BookMarkService by lazy {
        retrofit.create(BookMarkService::class.java)
    }
}