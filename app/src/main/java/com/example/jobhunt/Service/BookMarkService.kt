package com.example.jobhunt.Service

import com.example.jobhunt.dataModel.BookMarkData
import com.example.jobhunt.dataModel.BookMarkResponse
import retrofit2.http.*

interface BookMarkService {

    @Headers("Content-Type: application/json")
    @POST("/bookmark/")
    suspend fun saveBookMark(@Body bookMark: BookMarkData): BookMarkResponse
    @DELETE("/api/bookmark/delete/{user_bookmark_id}")
    suspend fun deleteBookMark(@Path("user_bookmark_id") user_bookmark_id: Long): BookMarkResponse
}