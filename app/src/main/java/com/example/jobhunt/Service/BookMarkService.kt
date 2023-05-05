package com.example.jobhunt.Service

import com.example.jobhunt.dataModel.BookMarkData
import com.example.jobhunt.dataModel.BookMarkResponse
import retrofit2.http.*

interface BookMarkService {

    @POST("api/bookmark/save")
    suspend fun saveBookmark(@Body bookmarkData: BookMarkData): BookMarkResponse

    @DELETE("api/bookmark/delete/{user_bookmark_id}")
    suspend fun deleteBookmark(@Path("user_bookmark_id") userBookmarkId: Long): BookMarkResponse
}