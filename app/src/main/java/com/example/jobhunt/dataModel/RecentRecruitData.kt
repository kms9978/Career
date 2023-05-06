package com.example.jobhunt.dataModel

import com.google.gson.annotations.SerializedName
import org.json.JSONObject


data class RecentRecruit(
    @SerializedName("state")
    val state: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("position")
    val position: String,
    @SerializedName("plan")
    val plan: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("img")
    val imgUrl: String,
    val companyName: String
)