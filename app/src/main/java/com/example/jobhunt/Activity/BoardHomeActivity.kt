package com.example.jobhunt.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.viewpager2.widget.ViewPager2
import com.example.jobhunt.Adapter.ViewPagerAdapter
import com.example.jobhunt.R
import com.example.jobhunt.databinding.ActivityBoardHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class BoardHomeActivity : AppCompatActivity() {
    private val binding: ActivityBoardHomeBinding by lazy { ActivityBoardHomeBinding.inflate(layoutInflater) }

    private val tabTextList = listOf("홈", "나의 글")
    //private val tabIconList = listOf(R.drawable.icon_profile, R.drawable.icon_search, R.drawable.icon_setting)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.viewPager01.adapter = ViewPagerAdapter(this)

        val goHome_Btn : ImageButton = findViewById(R.id.goHome_Btn)

        goHome_Btn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


        TabLayoutMediator(binding.tabLayout01, binding.viewPager01) { tab, pos ->
            tab.text = tabTextList[pos]
        }.attach()

        // 처음, 마지막 페이지간 양방향 이동 가능
        binding.viewPager01.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            var currentState = 0
            var currentPos = 0

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if(currentState == ViewPager2.SCROLL_STATE_DRAGGING && currentPos == position) {
                    if(currentPos == 0) binding.viewPager01.currentItem = 1
                    else if(currentPos == 1) binding.viewPager01.currentItem = 0
                }
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                currentPos = position
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                currentState = state
                super.onPageScrollStateChanged(state)
            }
        })
    }
}