package com.example.jobhunt.dataModel

import com.google.gson.annotations.SerializedName

data class BookMarkListResponse(
    @SerializedName("data") val data: List<BookMarkData>,
    @SerializedName("message") val message: String?,
    @SerializedName("status") val status: Int
)