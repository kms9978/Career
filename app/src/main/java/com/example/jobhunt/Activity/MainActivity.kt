package com.example.jobhunt.Activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.jobhunt.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // Handle the splash screen transition.
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        window.statusBarColor = Color.parseColor("#ffffff")

        val login_Btn = findViewById<Button>(R.id.Login_Btn)
        val go_Register = findViewById<TextView>(R.id.go_Register)

        //Register intent
        fun moveToRegisterPage(){
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        //intent
        fun moveToHomePage(){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        //함수 호출
        login_Btn.setOnClickListener{
            moveToHomePage()
        }
        go_Register.setOnClickListener {
            moveToRegisterPage()
        }


    }
}
