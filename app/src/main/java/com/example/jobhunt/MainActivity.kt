package com.example.jobhunt

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.window.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // Handle the splash screen transition.
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        window.statusBarColor = Color.parseColor("#ffffff")

        val login_Btn = findViewById<Button>(R.id.Login_Btn)

        //intent
        fun moveToHomePage(){
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }
        //함수 호출
        login_Btn.setOnClickListener{
            moveToHomePage()
        }


    }
}
