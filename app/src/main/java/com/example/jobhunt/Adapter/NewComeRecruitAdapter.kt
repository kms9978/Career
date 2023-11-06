package com.example.jobhunt.Adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobhunt.R
import com.example.jobhunt.DataModel.RecentRecruit

class NewComeRecruitAdapter(
    private var itemList: List<RecentRecruit> = emptyList()) : RecyclerView.Adapter<NewComeRecruitAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val companyNameView: TextView = itemView.findViewById(R.id.recruit_name)
        val contentView: TextView = itemView.findViewById(R.id.recruit_title)
        val companydate : TextView = itemView.findViewById(R.id.expire_date)

        init {
            itemView.setOnClickListener {
                val url = itemList[adapterPosition].link // 클릭한 항목의 링크 가져오기
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.jobkorea.co.kr$url")) // 링크intent domain+
                it.context.startActivity(intent) // 인텐트 실행
            }
        }
    }

    fun setNewComeRecruitDataList(newItemList: List<RecentRecruit>) {
        itemList = newItemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_newcom, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.companyNameView.text = item.companyName // Key값으로 companyName을 사용하여 설정
        holder.contentView.text = item.content
        holder.companydate.text = item.plan

        if (item.position == "신입") {
            holder.companyNameView.text = item.companyName
            holder.contentView.text = item.content
            holder.companydate.text = item.plan
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}