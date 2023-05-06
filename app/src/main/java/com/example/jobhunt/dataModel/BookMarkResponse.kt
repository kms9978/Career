package com.example.jobhunt.dataModel

import com.google.gson.annotations.SerializedName

data class BookMarkResponse(
    val success: Boolean,
    val message: String,
    val user_bookmark_id: Long // 새로 추가된 북마크의 ID를 포함시킵니다.
)