package com.example.jobhunt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.jobhunt.dataModel.CodenaryData
import com.example.jobhunt.databinding.ActivityDetailcodenaryBinding

class DetailcodenaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailcodenaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailcodenaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val codenaryData = intent.getParcelableExtra<CodenaryData>("codenary_data")

        binding.detailTitleText.text = codenaryData?.title
        binding.detailInfoText.text = codenaryData?.info
        binding.detailDateText.text = codenaryData?.date
        Glide.with(this)
            .load(codenaryData?.logo)
            .into(binding.detailImage)
    }
}