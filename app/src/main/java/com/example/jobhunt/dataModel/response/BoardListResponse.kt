package com.example.jobhunt.DataModel.response

import com.example.jobhunt.DataModel.BoardData
import com.google.gson.annotations.SerializedName

data class BoardListResponse (
    @SerializedName("data")
    val boardList: List<BoardData>
)