package com.example.jobhunt.dataModel

data class RecentRecruit(
    // Json의 필드순서와 일치해야됨
    val companyName: String,
    val content: String,
    val position: String,
    val plan: String,
    val url: String,
    val imgUrl: String,
)