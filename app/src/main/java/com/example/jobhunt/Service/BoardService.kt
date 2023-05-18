package com.example.jobhunt.Service

import com.example.jobhunt.dataModel.BoardData
import com.example.jobhunt.dataModel.BoardResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface BoardService {
    @POST("api/board/write")
    fun addBoard(@Body boardData: BoardData) : Call<BoardResponse>

}