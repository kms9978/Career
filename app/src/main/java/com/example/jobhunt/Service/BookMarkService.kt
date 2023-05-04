package com.example.jobhunt.Service

import com.example.jobhunt.dataModel.BookMarkData
import com.example.jobhunt.dataModel.ResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface BookMarkService {

    @POST("api/bookmark/save")
    fun saveBookmark(@Body bookmarkData: BookMarkData): Call<Void>

    @DELETE("api/bookmark/delete/{user_bookmark_id}")
    fun deleteBookmark(@Path("user_bookmark_id") userBookmarkId: Long): Call<Void>

}