package com.example.jobhunt.dataModel


data class Bookmark(
    val userBookmarkId: Long,
    val bookmarkImg: String,
    val bookmarkName: String,
    val bookmarkEndDate: String,
    val bookmarkStartDate: String,
    val companyLink: String
)