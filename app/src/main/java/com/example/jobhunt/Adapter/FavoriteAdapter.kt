package com.example.jobhunt.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jobhunt.Utils.RetrofitClient
import com.example.jobhunt.R
import com.example.jobhunt.DataModel.BookMarkData
import com.example.jobhunt.DataModel.response.BookMarkListResponse
import com.example.jobhunt.DataModel.response.BookMarkResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FavoriteAdapter(
    private val context: Context,
        private var bookmarkList: List<BookMarkData> = emptyList(),
    private var token: String? = null
) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private val bookmarkDataList = mutableListOf<BookMarkData>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bookMarkName: TextView = itemView.findViewById(R.id.bookmark_name)
        private val bookMarkStartDate: TextView = itemView.findViewById(R.id.bookmark_start_date)
        private val bookMarkEndDate: TextView = itemView.findViewById(R.id.bookmark_end_date)
        private val bookMarkImg: ImageView = itemView.findViewById(R.id.bookmark_img)
        private val deleteBookmark: ImageButton = itemView.findViewById(R.id.delete_bookmark)

        fun bind(item: BookMarkData, adapter: FavoriteAdapter) {
            bookMarkName.text = item.bookMarkName
            bookMarkStartDate.text = item.bookMarkStartDate
            bookMarkEndDate.text = item.bookMarkEndDate
            Glide.with(itemView)
                .load(item.bookMarkImg)
                .into(bookMarkImg)

            deleteBookmark.setOnClickListener {
                adapter.deleteBookmark(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_bookmark, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bookmark = bookmarkDataList[position]
        holder.bind(bookmark, this)
    }

    override fun getItemCount(): Int {
        return bookmarkDataList.size
    }

    fun updateData(bookmarkList: List<BookMarkData>, token: String?) {
        this.bookmarkList = bookmarkList
        this.token = token
        val service = RetrofitClient(context).retrofitService
        val call = service.getBookmarks()

        call.enqueue(object : Callback<List<BookMarkListResponse>> {
            override fun onResponse(call: Call<List<BookMarkListResponse>>, response: Response<List<BookMarkListResponse>>) {
                if (response.isSuccessful) {
                    val bookmarkList = response.body()
                    val bookmarkResponse = bookmarkList?.getOrNull(0)
                    val bookmarkArray = bookmarkResponse?.bookmark
                    if (bookmarkArray != null) {
                        bookmarkDataList.clear()
                        bookmarkDataList.addAll(bookmarkArray)
                        notifyDataSetChanged()
                    }
                } else {
                    Log.d("FavoriteFragment", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<BookMarkListResponse>>, t: Throwable) {
                Log.d("FavoriteFragment", "Error: ${t.message}")
            }
        })
    }
    fun deleteBookmark(item: BookMarkData) {
        val service = RetrofitClient(context).retrofitService
        val call = service.deleteBookMark(item.user_bookmark_id)
        call.enqueue(object : Callback<BookMarkResponse> {
            override fun onResponse(call: Call<BookMarkResponse>, response: Response<BookMarkResponse>) {
                if (response.isSuccessful) {
                    bookmarkDataList.remove(item)
                    notifyDataSetChanged()
                } else {
                    Log.d("FavoriteFragment", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<BookMarkResponse>, t: Throwable) {
                Log.d("FavoriteFragment", "Error: ${t.message}")
            }
        })
    }
}