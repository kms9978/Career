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
    private var context: Context,
    private var itemList: List<BookMarkData> = emptyList()) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 뷰홀더에서 사용할 View들을 바인딩합니다.
        private val bookMarkName: TextView = itemView.findViewById(R.id.bookmark_name)
        private val bookMarkStartDate: TextView = itemView.findViewById(R.id.bookmark_start_date)
        private val bookMarkEndDate: TextView = itemView.findViewById(R.id.bookmark_end_date)
        private val bookMarkImg: ImageView = itemView.findViewById(R.id.bookmark_img)

        // 데이터를 뷰에 바인딩하는 bind() 메서드를 추가합니다.
        fun bind(item: BookMarkData) {
            bookMarkName.text = item.bookMarkName
            bookMarkStartDate.text = item.bookMarkStartDate
            bookMarkEndDate.text = item.bookMarkEndDate
            // 이미지를 로드하여 ImageView에 표시합니다.
            Glide.with(itemView)
                .load(item.bookMarkImg)
                .into(bookMarkImg)
        }
    }


    fun setData(items: List<BookMarkData>) {
        itemList = items
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_bookmark, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // ViewHolder의 bind() 메서드를 호출하여 데이터를 뷰에 바인딩합니다.
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}