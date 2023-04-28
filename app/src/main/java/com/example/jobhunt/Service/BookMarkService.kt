package com.example.jobhunt.Service

import com.example.jobhunt.dataModel.Bookmark
import retrofit2.http.Body
import retrofit2.http.POST

interface BookMarkService {
    @POST("api/bookmark/save")
    suspend fun addBookmark(
        @Body bookmark: Bookmark
    ): Bookmark
}