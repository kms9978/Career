package com.example.jobhunt.DataModel

import com.google.gson.annotations.SerializedName


data class BookMarkData(
    @SerializedName("user_bookmark_id") val user_bookmark_id: Long,
    @SerializedName("bookMarkName") val bookMarkName: String,
    @SerializedName("company_link") val company_link: String,
    @SerializedName("bookMarkImg") val bookMarkImg: String,
    @SerializedName("bookMark_End_Date") val bookMarkEndDate: String,
    @SerializedName("bookMark_Start_Date") val bookMarkStartDate: String,
    var isChecked: Boolean // 체크 여부를 저장하는 속성
)