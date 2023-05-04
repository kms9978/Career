package com.example.jobhunt.Service

import com.example.jobhunt.dataModel.BookMarkData
import com.example.jobhunt.dataModel.ResponseData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface BookMarkService {

    @POST("/api/bookmark/save")
    fun saveBookmark(@Body bookmarkData: BookMarkData): Call<Void>

    @GET("/api/bookmark/save")
    fun getBookmarks(): Call<List<BookMarkData>>

    @DELETE("/api/bookmark/{user_bookmark_id}")
    fun deleteBookmark(@Path("user_bookmark_id") userBookmarkId: Long): Call<Void>

}