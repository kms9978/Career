package com.example.jobhunt.dataModel

import com.google.gson.annotations.SerializedName

data class BoardListResponse (
    @SerializedName("data")
    val boardList: List<BoardData>
)