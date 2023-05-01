package com.example.jobhunt.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jobhunt.R
import com.example.jobhunt.dataModel.CodenaryData
import retrofit2.Call
import retrofit2.Response

class CodenaryAdapter(private var dataList: List<CodenaryData> = listOf()) :
    RecyclerView.Adapter<CodenaryAdapter.ViewHolder>() {


    fun setData(newsList: List<CodenaryData>) {
        dataList = newsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_codenary, parent, false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = dataList[position]
        holder.title.text = news.title
        holder.content.text = news.info
        holder.date.text = news.date
        Glide.with(holder.itemView.context)
            .load(news.logo)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.codenary_title)
        val content: TextView = view.findViewById(R.id.codenary_content)
        val date: TextView = view.findViewById(R.id.codenary_date)
        val image: ImageView = view.findViewById(R.id.codenary_Img)
    }
}