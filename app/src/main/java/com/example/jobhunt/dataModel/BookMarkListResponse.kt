package com.example.jobhunt.dataModel

import com.google.gson.annotations.SerializedName

data class BookMarkListResponse(
    @SerializedName("bookmark")
    val bookmark: List<BookMarkData>
)