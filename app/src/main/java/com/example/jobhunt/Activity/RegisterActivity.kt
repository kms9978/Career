package com.example.jobhunt.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jobhunt.R
import com.example.jobhunt.Service.ApiService
import com.example.jobhunt.Utils.EmailRetrofit
import com.example.jobhunt.Service.EmailService
import com.example.jobhunt.DataModel.UserJsonData
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// RegisterActivity 클래스 선언, AppCompatActivity 상속
class RegisterActivity : AppCompatActivity() {
    private lateinit var emailService: EmailService
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var context : Context
    // onCreate() 메서드 오버라이드
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // activity_register 레이아웃 파일을 화면에 띄움
        setContentView(R.layout.activity_register)

        emailService = EmailRetrofit.getEmailService()
        sharedPreferences = getSharedPreferences("auth_number", Context.MODE_PRIVATE)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val editor = sharedPreferences.edit()
            editor.remove("is_authenticated")
            editor.apply()
            Log.d("is_authenticated_delete", "인증여부 삭제됨")
        }, 10000) // 10초 후에 실행되도록 설정

        val retrofit = Retrofit.Builder()
            .baseUrl("http://54.227.205.92:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // com.example.jobhunt.Service.ApiService 인터페이스를 구현한 객체를 생성
        val service = retrofit.create(ApiService::class.java)

        // 뷰 요소들을 변수에 할당
        val Register_Btn: Button = findViewById(R.id.Register_Btn)
        Register_Btn.isEnabled = sharedPreferences.getBoolean("is_authenticated", false)

        val edt_id: EditText = findViewById(R.id.edt_id)
        val edt_nick: EditText = findViewById(R.id.edt_nick)
        val edt_pass: EditText = findViewById(R.id.edt_pass)
        val back_btn: ImageButton = findViewById(R.id.back_btn)
        val send_email: Button = findViewById(R.id.send_email)
        val edt_email : EditText = findViewById(R.id.edt_email)
        val auth_email : EditText = findViewById(R.id.auth_email)
        val auth_btn : Button = findViewById(R.id.auth_btn)

        var isButtonClickable = true
        var countDownTimer: CountDownTimer? = null

        back_btn.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }


        send_email.setOnClickListener {
            if (isButtonClickable) {
                // 버튼 클릭 처리
                isButtonClickable = false
                send_email.isEnabled = false
                // 30초 후에 다시 클릭 가능하도록 설정
                countDownTimer = object : CountDownTimer(30000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val secondsRemaining = millisUntilFinished / 1000
                        send_email.text = "인증 (${secondsRemaining}s)"
                    }
                    override fun onFinish() {
                        isButtonClickable = true
                        send_email.isEnabled = true
                        send_email.text = "인증"
                    }
                }
                countDownTimer?.start()
                val email = edt_email.text.toString()
                // Retrofit을 이용하여 서버에 이메일 전송

                emailService.sendEmail(email).enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response.isSuccessful) {
                            val authNumber = response.body()
                            if (authNumber != null) {
                                // 서버에서 받은 인증 번호를 SharedPreferences에 저장
                                Log.d("AuthNumber", "Stored auth number: $authNumber")
                                sharedPreferences.edit().putString("auth_number", authNumber).apply()
                                Toast.makeText(applicationContext, "인증번호가 전송되었습니다.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(applicationContext, "이메일 전송에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Toast.makeText(applicationContext, "이메일 전송에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        // 이메일 인증 버튼 클릭 시
        auth_btn.setOnClickListener {
            val authCode = auth_email.text.toString().trim()

            // SharedPreferences에서 인증코드 가져오기
            val pref = getSharedPreferences("auth_number", Context.MODE_PRIVATE)
            val savedCode = pref.getString("auth_number", null)

            if (authCode.isEmpty()) {
                Toast.makeText(applicationContext, "인증코드를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else if (authCode == savedCode) {
                Toast.makeText(applicationContext, "인증에 성공했습니다.", Toast.LENGTH_SHORT).show()

                // SharedPreferences에 인증여부 저장
                val editor = pref.edit()
                editor.putBoolean("is_authenticated", true)
                editor.apply()
                Log.d("is_authenticated_what", "인증여부 저장됨: true")

                // Register_Btn 활성화
                Register_Btn.isEnabled = true


            } else {
                Toast.makeText(applicationContext, "인증코드가 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                Register_Btn.isEnabled = false
            }
        }

        // Register_Btn 버튼이 클릭되었을 때 실행될 동작 정의
        Register_Btn.setOnClickListener {

            val isAuth = sharedPreferences.getBoolean("is_authenticated", false)
            if (!isAuth) { // 인증이 되지 않았을 경우
                Toast.makeText(applicationContext, "인증을 먼저 실행해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // 함수를 빠져나감
            }

            // 입력한 텍스트 값을 변수에 할당하고 양 옆의 공백 제거
            val username = edt_id.text.toString().trim()
            val nickname = edt_nick.text.toString().trim()
            val password = edt_pass.text.toString().trim()
            // 모든 필드에 값을 입력하지 않았을 경우 토스트 메시지 출력
                if (username.isEmpty() || nickname.isEmpty() || password.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
            else {

                //UserJsonData 클래스에서 createJsonObject 함수를 호출하여 JSON 객체를 생성하고, 해당 객체를 jsonObject 변수에 할당
                val jsonObject = UserJsonData.createJsonObject(username, nickname, password)

                // 서버로 전송할 JSON 객체를 RequestBody로 변환
                // toMediaTypeOrNull() 함수를 통해 요청의 Content-Type을 application/json으로 지정.
                val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

                // 서버에 데이터 전송하고 응답 처리
                service.postData(requestBody).enqueue(object : Callback<ResponseBody> {
                    // 응답 받았을 때 호출되는 메서드
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) { // 응답 성공 시 토스트 메시지 출력
                            Toast.makeText(applicationContext, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                        } else { // 응답 실패 시 토스트 메시지 출력
                            Toast.makeText(applicationContext, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    // 응답에 실패했을 때 호출되는 메서드
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        t.printStackTrace() // 오류 로그 출력
                    }
                })
            }
        }
    }


    
}