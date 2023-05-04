package com.example.jobhunt.dataModel


data class BookMarkData(
    val user_bookmark_id: Long?,
    val bookMarkImg: String?,
    val bookMarkName: String?,
    val bookMark_End_Date: String?,
    val bookMark_Start_Date: String?,
    val company_link: String?
) {
    init {
        requireNotNull(bookMarkName) { "bookMarkName must not be null" }
    }
}