package com.example.jobhunt.dataModel

import com.google.gson.annotations.SerializedName


data class RecentRecruit(
    val companyName: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("position")
    val position: String,
    @SerializedName("plan")
    val plan: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("img")
    val imgUrl: String,
    var bookmarked: Boolean = false
)