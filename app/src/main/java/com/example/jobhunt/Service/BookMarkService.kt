package com.example.jobhunt.Service

import com.example.jobhunt.dataModel.BookMarkData
import com.example.jobhunt.dataModel.BookMarkResponse
import com.example.jobhunt.dataModel.RecentRecruit
import retrofit2.Call
import retrofit2.http.*

interface BookMarkService {

    @POST("api/bookmark/save")
    fun saveBookMark(@Body bookMarkData: BookMarkData): Call<BookMarkResponse>

    @DELETE("api/bookmark/delete/{user_bookmark_id}")
    fun deleteBookMark(@Path("user_bookmark_id") user_bookmark_id: Long): Call<BookMarkResponse>


    companion object {
        fun BookMark(recentRecruit: RecentRecruit): BookMarkData {
            val bookmarkImg = recentRecruit.imgUrl
            val bookmarkName = recentRecruit.companyName
            val bookmarkDates = recentRecruit.plan.split("~")
            val bookmarkStartDate = bookmarkDates[0].trim()
            val bookmarkEndDate = bookmarkDates[1].trim()
            val companyLink = recentRecruit.url

            return BookMarkData(
                user_bookmark_id = 0L, // 새로운 북마크를 추가할 때는 ID 값을 자동 생성해야 합니다.
                bookMarkImg = bookmarkImg,
                bookMarkName = bookmarkName,
                bookMark_Start_Date = bookmarkStartDate,
                bookMark_End_Date = bookmarkEndDate,
                company_link = companyLink
            )
        }
    }
}