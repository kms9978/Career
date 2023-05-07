package com.example.jobhunt.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jobhunt.R
import com.example.jobhunt.dataModel.BookMarkData
import com.example.jobhunt.dataModel.BookMarkListResponse


class FavoriteAdapter(
    private val context: Context,
    private var bookmarkList: List<BookMarkData> = emptyList()
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

    fun updateData(bookmarks: List<BookMarkData>) {
        bookmarkList = bookmarks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_bookmark, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bookmarkList[position])
    }

    override fun getItemCount(): Int {
        return bookmarkList.size
    }
}