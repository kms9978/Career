package com.example.jobhunt.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.jobhunt.HomeFragment
import com.example.jobhunt.R
import com.example.jobhunt.data.RecentRecruitData

class RecentRecruitAdapter(private val context: HomeFragment) :
RecyclerView.Adapter<RecentRecruitAdapter.ViewHolder>(){

    var datas = mutableListOf<RecentRecruitData>()
    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int): ViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recent,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder : ViewHolder, position: Int){
        holder.bind(datas[position])
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val txtName: TextView = itemView.findViewById(R.id.recruit_name);
        private val txtTitle : TextView =itemView.findViewById(R.id.recruit_title);

        fun bind(item: RecentRecruitData){
            txtName.text = item.name
            txtTitle.text = item.title
        }
    }

}