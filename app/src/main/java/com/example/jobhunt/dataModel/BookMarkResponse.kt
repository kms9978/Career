package com.example.jobhunt.dataModel

import com.google.gson.annotations.SerializedName

data class BookMarkResponse(
    val success: Boolean,
    val message: String,
    val user_bookmark_id: Long
)