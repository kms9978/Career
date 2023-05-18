package com.example.jobhunt.dataModel

import com.google.gson.annotations.SerializedName

data class BoardData (
    @SerializedName("board_id")
    val board_id : Long,
    @SerializedName("content")
    val content : String,
    @SerializedName("preview")
    val preview : String,
    @SerializedName("subject")
    val subject : String,
    @SerializedName("title")
    val title : String,
    @SerializedName("writer")
    val writer : String

)