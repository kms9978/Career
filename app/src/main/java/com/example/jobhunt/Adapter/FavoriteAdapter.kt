package com.example.jobhunt.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jobhunt.Fragment.RetrofitClient
import com.example.jobhunt.R
import com.example.jobhunt.dataModel.BookMarkData
import com.example.jobhunt.dataModel.BookMarkListResponse
import org.json.JSONArray
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FavoriteAdapter(
    private val context: Context,
    private var bookmarkList: List<BookMarkData> = emptyList(),
    private var token: String? = null
) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bookMarkName: TextView = itemView.findViewById(R.id.bookmark_name)
        private val bookMarkStartDate: TextView = itemView.findViewById(R.id.bookmark_start_date)
        private val bookMarkEndDate: TextView = itemView.findViewById(R.id.bookmark_end_date)
        private val bookMarkImg: ImageView = itemView.findViewById(R.id.bookmark_img)

        fun bind(item: BookMarkData) {
            bookMarkName.text = item.bookMarkName
            bookMarkStartDate.text = item.bookMarkStartDate
            bookMarkEndDate.text = item.bookMarkEndDate
            Glide.with(itemView)
                .load(item.bookMarkImg)
                .into(bookMarkImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_bookmark, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bookmark = bookmarkList[position]
        holder.bind(bookmark)

        val service = RetrofitClient(context).retrofitService
        val call = service.getBookmarks()

        call.enqueue(object : Callback<BookMarkListResponse> {
            override fun onResponse(call: Call<BookMarkListResponse>, response: Response<BookMarkListResponse>) {
                if (response.isSuccessful) {
                    val bookmarkResponse = response.body()
                    val updatedBookmark = bookmarkResponse?.bookmark?.find { it.user_bookmark_id == bookmark.user_bookmark_id }
                    if (updatedBookmark != null) {
                        holder.bind(updatedBookmark)
                    }
                } else {
                    Log.d("FavoriteFragment", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<BookMarkListResponse>, t: Throwable) {
                Log.d("FavoriteFragment", "Error: ${t.message}")
            }
        })
    }

    override fun getItemCount(): Int {
        return bookmarkList.size
    }

    fun updateData(jsonString: List<BookMarkData>, token: String?) {
        try {
            val jsonArray = JSONArray(jsonString)
            val jsonObject = jsonArray.getJSONObject(0)
            val bookmarkArray = jsonObject.getJSONArray("bookmark")

            val bookmarks = mutableListOf<BookMarkData>()

            for (i in 0 until bookmarkArray.length()) {
                val bookmarkObject = bookmarkArray.getJSONObject(i)
                val bookMarkImg = bookmarkObject.getString("bookMarkImg")
                val bookMarkName = bookmarkObject.getString("bookMarkName")
                val bookMarkStartDate = bookmarkObject.getString("bookMark_Start_Date")
                val bookMarkEndDate = bookmarkObject.getString("bookMark_End_Date")
                val companyLink = bookmarkObject.getString("company_link")
                val userBookmarkId = bookmarkObject.getLong("user_bookmark_id").toLong()
                val bookmark = BookMarkData( bookMarkImg, bookMarkName, bookMarkStartDate,bookMarkEndDate,companyLink,userBookmarkId )
                bookmarks.add(bookmark)
            }

            bookmarkList = bookmarks
            this.token = token
            notifyDataSetChanged()

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }
}