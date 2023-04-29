package com.example.jobhunt.Service

import com.example.jobhunt.dataModel.BookMarkData
import com.example.jobhunt.dataModel.ResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface BookMarkService {
    @POST("/api/bookmark/save")
    fun addBookmark(@Body bookmarkData: BookMarkData): Call<ResponseData>
}