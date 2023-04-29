package com.example.jobhunt.dataModel

data class ResponseData(
    val success: Boolean,
    val message: String,
    val bookmark: BookMarkData?
)