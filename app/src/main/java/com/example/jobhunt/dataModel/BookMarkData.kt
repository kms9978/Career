package com.example.jobhunt.dataModel

import com.google.gson.annotations.SerializedName


data class BookMarkData(
    val user_bookmark_id: Long,
    val bookMarkImg: String?,
    val bookMarkName: String?,
    val bookMark_End_Date: String?,
    val bookMark_Start_Date: String?,
    val company_link: String?,
){

}