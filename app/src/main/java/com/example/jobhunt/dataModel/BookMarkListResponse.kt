package com.example.jobhunt.dataModel

import com.google.gson.annotations.SerializedName

data class BookMarkListResponse(
    @SerializedName("bookmarks") val bookmarks: List<BookMarkData>
)