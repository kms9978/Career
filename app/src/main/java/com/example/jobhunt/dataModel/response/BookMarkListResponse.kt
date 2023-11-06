package com.example.jobhunt.DataModel.response

import com.example.jobhunt.DataModel.BookMarkData
import com.google.gson.annotations.SerializedName

data class BookMarkListResponse(
    @SerializedName("bookmark")
    val bookmark: List<BookMarkData>
)
