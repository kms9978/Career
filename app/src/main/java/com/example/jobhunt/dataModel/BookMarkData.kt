package com.example.jobhunt.dataModel

import com.google.gson.annotations.SerializedName


data class BookMarkData(
    @SerializedName("bookMarkImg") val bookMarkImg: String,
    @SerializedName("bookMarkName") val bookMarkName: String?,
    @SerializedName("bookMark_Start_Date") val bookMarkStartDate: String,
    @SerializedName("bookMark_End_Date") val bookMarkEndDate: String,
    @SerializedName("company_link") val company_link: String?,
    @SerializedName("user_bookmark_id") val user_bookmark_id: Long,
)