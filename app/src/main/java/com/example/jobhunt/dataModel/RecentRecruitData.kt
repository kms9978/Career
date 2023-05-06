package com.example.jobhunt.dataModel

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import org.json.JSONObject


data class RecentRecruit(
    var companyName: String,
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
    val bookMarkData: BookMarkData? = null // BookMarkData 클래스의 인스턴스를 포함합니다.
)