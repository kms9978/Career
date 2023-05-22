package com.example.jobhunt.dataModel

import com.google.gson.annotations.SerializedName

data class BoardListResponse (
    @SerializedName("board")
    val bookmark: List<BoardData>
)