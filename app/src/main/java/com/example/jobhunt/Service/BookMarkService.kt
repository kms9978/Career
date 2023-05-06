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
            val bookmarkName = recentRecruit.companyName
            val company_link = recentRecruit.link // 수정된 부분
            val bookmarkImg = recentRecruit.imgUrl
            val bookmarkDates = recentRecruit.plan.split("~")
            val bookmarkStartDate = bookmarkDates[0].trim()
            val bookmarkEndDate = bookmarkDates[1].trim()


            return BookMarkData(
                bookMarkImg = bookmarkImg,
                bookMarkName = bookmarkName,
                bookMarkStartDate = bookmarkStartDate,
                bookMarkEndDate = bookmarkEndDate,
                company_link = company_link,
                user_bookmark_id = 0L,

            )
        }
    }
}