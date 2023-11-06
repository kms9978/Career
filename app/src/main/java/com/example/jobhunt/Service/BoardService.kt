package com.example.jobhunt.Service

import com.example.jobhunt.DataModel.BoardData
import com.example.jobhunt.DataModel.response.BoardListResponse
import com.example.jobhunt.DataModel.response.BoardResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BoardService {
    @POST("api/board/write")
    fun addBoard(@Body boardData: BoardData): Call<BoardResponse>

    @GET("api/board/list")
    fun getBoard(): Call<BoardListResponse>
}