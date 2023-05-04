package com.example.jobhunt.dataModel

import com.google.gson.annotations.SerializedName


data class RecentRecruit(
    val companyName: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("position")
    val position: String,
    @SerializedName("plan")
    val plan: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("img")
    val imgUrl: String,
    var isBookmarked: Boolean = false, // 북마크 여부를 나타내는 프로퍼티
    var bookmarkId: Long? = null // 북마크 ID를 나타내는 프로퍼티
) {
    companion object {
        fun List<RecentRecruit>.toBookMarkDataList(): List<BookMarkData> {
            return this.map { recentRecruitData ->
                val planDates = recentRecruitData.plan.split("~")
                BookMarkData(
                    user_bookmark_id = 0L, // user_bookmark_id는 서버에서 할당되므로 일단 0으로 설정합니다.
                    bookMarkImg = recentRecruitData.imgUrl,
                    bookMarkName = recentRecruitData.companyName,
                    bookMark_Start_Date = planDates[0].trim(),
                    bookMark_End_Date = planDates[1].trim(),
                    company_link = recentRecruitData.url
                )
            }
        }
    }
}