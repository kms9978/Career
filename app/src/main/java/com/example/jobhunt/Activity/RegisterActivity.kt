package com.example.jobhunt.Activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jobhunt.R
import com.example.jobhunt.Service.ApiService
import com.example.jobhunt.dataModel.UserJsonData
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


// RegisterActivity 클래스 선언, AppCompatActivity 상속
class RegisterActivity : AppCompatActivity() {

    // onCreate() 메서드 오버라이드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // activity_register 레이아웃 파일을 화면에 띄움
        setContentView(R.layout.activity_register)



        val retrofit = Retrofit.Builder()
            .baseUrl("http://54.227.205.92:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // com.example.jobhunt.Service.ApiService 인터페이스를 구현한 객체를 생성
        val service = retrofit.create(ApiService::class.java)

        // 뷰 요소들을 변수에 할당
        val Register_Btn: Button = findViewById(R.id.Register_Btn)
        val edt_id: EditText = findViewById(R.id.edt_id)
        val edt_nick: EditText = findViewById(R.id.edt_nick)
        val edt_pass: EditText = findViewById(R.id.edt_pass)
        val back_btn: ImageButton = findViewById(R.id.back_btn)

        back_btn.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }


        // Register_Btn 버튼이 클릭되었을 때 실행될 동작 정의
        Register_Btn.setOnClickListener {
            // 입력한 텍스트 값을 변수에 할당하고 양 옆의 공백 제거
            val username = edt_id.text.toString().trim()
            val nickname = edt_nick.text.toString().trim()
            val password = edt_pass.text.toString().trim()

            // 모든 필드에 값을 입력하지 않았을 경우 토스트 메시지 출력
            if (username.isEmpty() || nickname.isEmpty() || password.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            } else {

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