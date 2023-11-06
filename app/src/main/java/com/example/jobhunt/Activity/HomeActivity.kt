package com.example.jobhunt.Activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.jobhunt.Fragment.GptFragment
import com.example.jobhunt.Fragment.FavoriteFragment
import com.example.jobhunt.Fragment.HomeFragment
import com.example.jobhunt.Fragment.RecruitFragment
import com.example.jobhunt.R
import com.example.jobhunt.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())
        window.statusBarColor = Color.parseColor("#000000")


        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.recruit -> replaceFragment(RecruitFragment())
                R.id.favorite -> replaceFragment(FavoriteFragment())
                R.id.gpt -> replaceFragment(GptFragment())

            else ->{

              }
            }

            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}