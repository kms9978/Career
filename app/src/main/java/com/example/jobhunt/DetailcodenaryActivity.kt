package com.example.jobhunt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.jobhunt.dataModel.CodenaryData
import com.example.jobhunt.databinding.ActivityDetailcodenaryBinding

class DetailcodenaryActivity : AppCompatActivity() {
    private val TAG = DetailcodenaryActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailcodenary)

        // CodenaryData 객체 가져오기
        val data = intent.getParcelableExtra<CodenaryData>("codenary_data")

        // 뷰에 데이터 설정
        findViewById<TextView>(R.id.detail_title_text).text = data?.title ?: ""
        Glide.with(this)
            .load(data?.logo)
            .into(findViewById<ImageView>(R.id.detail_image))
        findViewById<TextView>(R.id.detail_info_text).text = data?.info ?: ""
        findViewById<TextView>(R.id.detail_date_text).text = data?.date ?: ""

    }
}