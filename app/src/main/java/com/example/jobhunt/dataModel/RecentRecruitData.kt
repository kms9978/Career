package com.example.jobhunt.DataModel

import com.google.gson.annotations.SerializedName


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
    val bookMarkData: BookMarkData? = null, // BookMarkData 클래스의 인스턴스를 포함합니다.
    var isChecked: Boolean = false // 체크박스 상태를 저장하는 변수
)