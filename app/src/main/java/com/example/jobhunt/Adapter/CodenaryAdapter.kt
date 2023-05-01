package com.example.jobhunt.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jobhunt.DetailcodenaryActivity
import com.example.jobhunt.R
import com.example.jobhunt.dataModel.CodenaryData
import retrofit2.Call
import retrofit2.Response

class CodenaryAdapter(

    internal var dataList: List<CodenaryData> = listOf(),
    private var listener: OnItemClickListener? = null
) : RecyclerView.Adapter<CodenaryAdapter.ViewHolder>() {

    lateinit var context: Context

    fun setData(newsList: List<CodenaryData>) {
        dataList = newsList
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context)
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

        holder.itemView.setOnClickListener {
            listener?.onItemClick(holder.itemView, position)
        }
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

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun getItem(position: Int): CodenaryData {
        return dataList[position]
    }

}