package com.example.jobhunt.dataModel

import com.google.gson.annotations.SerializedName

data class BookMarkResponse(
    @SerializedName("data")
    val data: List<BookMarkData>,

    @SerializedName("message")
    val message: String,

    @SerializedName("success")
    val success: Boolean
)