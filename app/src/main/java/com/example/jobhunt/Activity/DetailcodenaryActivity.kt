package com.example.jobhunt.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.jobhunt.R
import com.example.jobhunt.DataModel.CodenaryData

class DetailcodenaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailcodenary)

        // Intent로 전달받은 CodenaryData 객체 가져오기
        val data = intent.getParcelableExtra<CodenaryData>("codenary_data")

        // 뒤로 가기 버튼 클릭 이벤트
        findViewById<ImageButton>(R.id.detail_back_btn).setOnClickListener {
            finish()
        }

        // 각 뷰에 데이터 설정
        findViewById<TextView>(R.id.detail_title_text).text = data?.title ?: ""
        Glide.with(this)
            .load(data?.logo) // Glide를 사용해 이미지 로드
            .into(findViewById<ImageView>(R.id.detail_image)) // ImageView에 이미지 설정
        findViewById<TextView>(R.id.detail_info_text).text = data?.info ?: ""
        findViewById<TextView>(R.id.detail_date_text).text = data?.date ?: ""
    }

}