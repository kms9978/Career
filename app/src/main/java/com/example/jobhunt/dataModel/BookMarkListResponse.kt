package com.example.jobhunt.dataModel

import com.google.gson.annotations.SerializedName

data class BookMarkListResponse(
//    @SerializedName("user_id")
//    val userId: Int,
//    @SerializedName("nickname")
//    val nickname: String,
//    @SerializedName("username")
//    val username: String,
    @SerializedName("bookmark")
    val bookmark: List<BookMarkData>
)
