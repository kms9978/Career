package com.example.jobhunt.Service

import com.example.jobhunt.dataModel.BoardData
import com.example.jobhunt.dataModel.BoardListResponse
import com.example.jobhunt.dataModel.BoardResponse
import com.example.jobhunt.dataModel.BookMarkListResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BoardService {
    @POST("api/board/write")
    fun addBoard(@Body boardData: BoardData) : Call<BoardResponse>

    @GET("api/board/list")
    fun getBoard() : Call<BoardListResponse>
}