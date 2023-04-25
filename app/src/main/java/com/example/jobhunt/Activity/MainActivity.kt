package com.example.jobhunt.Activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.edit
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.jobhunt.R
import com.example.jobhunt.Service.LoginService
import com.example.jobhunt.Service.RefreshTokenService
import com.example.jobhunt.dataModel.LoginRequest
import com.example.jobhunt.dataModel.LoginResponse
import com.example.jobhunt.dataModel.RefreshTokenRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://52.91.68.139:8888")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val loginService = retrofit.create(LoginService::class.java)
    private val refreshTokenService = retrofit.create(RefreshTokenService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        window.statusBarColor = Color.parseColor("#ffffff")

        val userId = findViewById<EditText>(R.id.userId)
        val userPass = findViewById<EditText>(R.id.userPass)
        val login_Btn = findViewById<Button>(R.id.Login_Btn)
        val go_Register = findViewById<TextView>(R.id.go_Register)

        //Register intent
        fun moveToRegisterPage() {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        //intent
        fun moveToHomePage() {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        // Login button click event
        login_Btn.setOnClickListener {
            val id = userId.text.toString()
            val password = userPass.text.toString()

            // Create a LoginRequest object
            val loginRequest = LoginRequest(username = id, password = password)

            loginService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        val token = loginResponse?.token ?: ""
                        val refreshToken = loginResponse?.refreshToken ?: ""

                        // Save the token and Refresh Token to SharedPreferences
                        val prefs = getSharedPreferences("auth", Context.MODE_PRIVATE)
                        prefs.edit {
                            putString("token", token)
                            putString("refreshToken", refreshToken)
                        }

                        // Redirect to HomeActivity
                        moveToHomePage()

                    } else {
                        // Login failed
                        Toast.makeText(this@MainActivity, "Login failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    // Login failed
                    Toast.makeText(this@MainActivity, "Login failed", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // Register button click event
        go_Register.setOnClickListener {
            moveToRegisterPage()
        }
    }

    // Refresh Token
    private fun refreshAccessToken() {
        val prefs = getSharedPreferences("auth", Context.MODE_PRIVATE)
        val refreshToken = prefs.getString("refreshToken", null)

        if (refreshToken != null) {
            // Create a RefreshTokenRequest object
            val refreshTokenRequest = RefreshTokenRequest(refreshToken)

            val retrofit = Retrofit.Builder()
                .baseUrl("http://52.91.68.139:8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val refreshTokenService = retrofit.create(RefreshTokenService::class.java)

            refreshTokenService.refreshAccessToken(refreshTokenRequest)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful) {
                            val loginResponse = response.body()
                            val token = loginResponse?.token ?: ""

                            // Save the new token to SharedPreferences
                            prefs.edit {
                                putString("token", token)
                            }
                        } else {
                            // Refresh Token failed
                            Toast.makeText(
                                this@MainActivity,
                                "Refresh Token failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        // Refresh Token failed
                        Toast.makeText(
                            this@MainActivity,
                            "Token Refresh failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }
}