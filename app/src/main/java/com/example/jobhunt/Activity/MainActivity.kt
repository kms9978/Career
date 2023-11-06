package com.example.jobhunt.Activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.edit
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.jobhunt.R
import com.example.jobhunt.Service.LoginService
import com.example.jobhunt.Service.RefreshTokenService
import com.example.jobhunt.Utils.AccessTokenInterceptor
import com.example.jobhunt.DataModel.request.LoginRequest
import com.example.jobhunt.DataModel.response.LoginResponse
import com.example.jobhunt.DataModel.request.RefreshTokenRequest
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity() {
    // Retrofit configuration
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://knu-carrer.org") // Retrofit baseURL 설정
        .addConverterFactory(GsonConverterFactory.create()) // JSON 파싱 설정
        .client(
            OkHttpClient.Builder()
                .addInterceptor(AccessTokenInterceptor(this)) // AccessTokenInterceptor 추가
                .build()
        )
        .build()

    // API service initialization
    private val loginService = retrofit.create(LoginService::class.java)
    private val refreshTokenService = retrofit.create(RefreshTokenService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide() // 액션바 숨기기
        window.statusBarColor = Color.parseColor("#ffffff") // 상태바 색상 변경

        // EditText, Button, TextView 등의 View 변수 초기화
        val userId = findViewById<EditText>(R.id.userId)
        val userPass = findViewById<EditText>(R.id.userPass)
        val login_Btn = findViewById<Button>(R.id.Login_Btn)
        val go_Register = findViewById<TextView>(R.id.go_Register)

        // 이동하는 인텐트 함수
        fun moveToRegisterPage() {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // 이동하는 인텐트 함수
        fun moveToHomePage() {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        // 로그인 버튼 클릭 이벤트
        login_Btn.setOnClickListener {
//            val id = userId.text.toString() // 사용자가 입력한 ID
//            val password = userPass.text.toString() // 사용자가 입력한 비밀번호
//
//            // LoginRequest 객체 생성
//            val loginRequest = LoginRequest(username = id, password = password)
//
//            // Retrofit을 이용하여 서버에 로그인 요청 보내기
//            loginService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
//                override fun onResponse(
//                    call: Call<LoginResponse>,
//                    response: Response<LoginResponse>
//                ) {
//                    if (response.isSuccessful) { // 로그인 성공
//                        val loginResponse = response.body() // 서버로부터 받은 LoginResponse 객체
//                        val token = loginResponse?.token ?: "" // LoginResponse 객체에서 토큰 가져오기
//                        val refreshToken = loginResponse?.refreshToken ?: "" // LoginResponse 객체에서 RefreshToken 가져오기
//                        // SharedPreferences에 토큰과 Refresh Token 저장하기
//                        val prefs = getSharedPreferences("auth", Context.MODE_PRIVATE)
//                        prefs.edit {
//                            putString("token", token)
//                            putString("refreshToken", refreshToken)
//                        }
//                        Log.d("TOKEN", "Token: $token")
//                        Log.d("REFRESHTOKEN", "RefreshToken: $refreshToken")
//                        // HomeActivity로 이동하기
//                        moveToHomePage()
//                    } else { // 로그인 실패
//                        Toast.makeText(this@MainActivity, "Login failed", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                    // 로그인 실패
//                    Toast.makeText(this@MainActivity, "Login failed", Toast.LENGTH_SHORT).show()
//                }
//            })

            moveToHomePage()
        }


        // Register button click event
        go_Register.setOnClickListener {
            moveToRegisterPage()

        }

        // 매 시간마다 Refresh Token
        val timer = Timer()
        val hourlyTask = object : TimerTask() {
            override fun run() {
                refreshAccessToken()
            }
        }
        timer.schedule(hourlyTask, 0L, 60 * 60 * 1000L)
    }
    // Refresh Token
    private fun refreshAccessToken() {
        val prefs = getSharedPreferences("auth", Context.MODE_PRIVATE)
        val refreshToken = prefs.getString("refreshToken", null)

        if (refreshToken != null) {
            // RefreshTokenRequest 객체 생성
            val refreshTokenRequest = RefreshTokenRequest(refreshToken)

            refreshTokenService.refreshAccessToken(refreshTokenRequest)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful) {
                            val loginResponse = response.body()
                            val token = loginResponse?.token ?: ""

                            // 새로운 토큰을 SharedPreferences에 저장
                            prefs.edit {
                                putString("token", token)
                            }
                        } else {
                            // Refresh Token 실패
//                            Toast.makeText(
//                                this@MainActivity,
//                                "Refresh Token 실패",
//                                Toast.LENGTH_SHORT
//                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        // Refresh Token 실패
//                        Toast.makeText(
//                            this@MainActivity,
//                            "Token Refresh 실패",
//                            Toast.LENGTH_SHORT
//                        ).show()
                    }
                })
        }
    }
}