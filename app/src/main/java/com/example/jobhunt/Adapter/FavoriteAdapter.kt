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

    private val bookmarkDataList = mutableListOf<BookMarkData>()

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
        val bookmark = bookmarkDataList[position]
        holder.bind(bookmark)
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
}