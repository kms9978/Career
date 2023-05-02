package com.example.jobhunt.dataModel

import org.jetbrains.annotations.NotNull

data class RecentRecruit(
    val companyName: String,
    val content: String,
    val position: String,
    val plan: String,
    val url: String,
    val imgUrl: String,
)