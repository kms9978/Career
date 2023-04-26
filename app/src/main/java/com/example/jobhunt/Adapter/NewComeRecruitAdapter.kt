package com.example.jobhunt.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobhunt.R
import com.example.jobhunt.dataModel.RecentRecruit

class NewComeRecruitAdapter(private val itemList: List<RecentRecruit>) : RecyclerView.Adapter<NewComeRecruitAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val companyNameView: TextView = itemView.findViewById(R.id.recruit_name)
        val contentView: TextView = itemView.findViewById(R.id.recruit_title)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_newcom, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        holder.companyNameView.text = item.companyName
        holder.contentView.text = item.content

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}