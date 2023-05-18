package com.example.jobhunt.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.jobhunt.R

class AddBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addboard)

        val back_btn : ImageButton = findViewById(R.id.back_btn)

        back_btn.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
    }
}