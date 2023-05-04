package com.example.jobhunt.Service

import com.example.jobhunt.dataModel.BookMarkData
import com.example.jobhunt.dataModel.BookMarkResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface BookMarkApiService {
    @POST("save-bookmark")
    suspend fun saveBookMark(@Body bookmark: BookMarkData): Response<BookMarkResponse>
}