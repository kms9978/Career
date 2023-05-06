package com.example.jobhunt.dataModel

import com.google.gson.annotations.SerializedName
import org.json.JSONObject


data class RecentRecruit(
    val companyName: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("position")
    val position: String,
    @SerializedName("plan")
    val plan: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("img")
    val imgUrl: String
){
    constructor(jsonObject: JSONObject, companyName: String) : this(
        companyName = companyName,
        content = jsonObject.getString("content"),
        position = jsonObject.getString("position"),
        plan = jsonObject.getString("plan"),
        link = jsonObject.getString("link"),
        imgUrl = jsonObject.getString("img") // imgUrl 변수로 변경
    )
}