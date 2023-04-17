package com.example.jobhunt.Activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.example.jobhunt.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val back_btn = findViewById<ImageButton>(R.id.back_btn)

        fun moveToMainPage(){
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        back_btn.setOnClickListener{
            moveToMainPage()
        }

        window.statusBarColor = Color.parseColor("#6B66FF")
    }
}