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
import com.example.jobhunt.DataModel.CodenaryData

class CodenaryAdapter(
    internal var dataList: List<CodenaryData> = listOf(), // 데이터 리스트
    private var listener: OnItemClickListener? = null // 아이템 클릭 리스너
) : RecyclerView.Adapter<CodenaryAdapter.ViewHolder>() {

    lateinit var context: Context // 액티비티의 Context 저장할 변수

    fun setData(newsList: List<CodenaryData>) { // 데이터 리스트 갱신 메서드
        dataList = newsList
        notifyDataSetChanged() // 어댑터 갱신
    }

    fun setOnItemClickListener(listener: OnItemClickListener) { // 아이템 클릭 리스너 설정 메서드
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { // 뷰 홀더 생성 메서드
        context = parent.context // 액티비티의 Context 변수에 저장
        val view = LayoutInflater.from(context) // 인플레이터를 이용해 뷰 생성
            .inflate(R.layout.item_codenary, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { // 뷰 홀더 데이터 설정 메서드
        val news = dataList[position] // 해당 위치의 데이터 객체 가져오기
        holder.title.text = news.title // 제목 텍스트뷰에 제목 설정
        Glide.with(holder.itemView.context) // 이미지 설정을 위한 Glide 라이브러리 활용
            .load(news.logo)
            .into(holder.image)

        holder.content.text = news.info // 내용 텍스트뷰에 내용 설정
        holder.date.text = news.date // 날짜 텍스트뷰에 날짜 설정

        holder.itemView.setOnClickListener { // 아이템 클릭 리스너 등록
            listener?.onItemClick(holder.itemView, position)
        }
    }

    override fun getItemCount(): Int { // 아이템 개수 반환
        return dataList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) { // 뷰 홀더 클래스
        val title: TextView = view.findViewById(R.id.codenary_title) // 뷰 홀더 내부에 각 뷰 저장
        val content: TextView = view.findViewById(R.id.codenary_content)
        val date: TextView = view.findViewById(R.id.codenary_date)
        val image: ImageView = view.findViewById(R.id.codenary_Img)
    }

    interface OnItemClickListener { // 아이템 클릭 리스너 인터페이스
        fun onItemClick(view: View, position: Int)
    }
}