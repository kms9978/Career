package com.example.jobhunt.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobhunt.Adapter.GptAdapter
import com.example.jobhunt.R
import com.example.jobhunt.Utils.ChatRetrofit
import com.example.jobhunt.Service.ChatService
import com.example.jobhunt.DataModel.Chat
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GptFragment : Fragment() {

    private lateinit var messageEditText: EditText
    private lateinit var sendButton: ImageButton

    private val gptAdapter = GptAdapter()

    private lateinit var chatService: ChatService
    private lateinit var chatRetrofit: ChatRetrofit

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gpt, container, false)

        messageEditText = view.findViewById(R.id.message_edit_text)
        sendButton = view.findViewById(R.id.send_btn)

        recyclerView = view.findViewById(R.id.chat_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = gptAdapter


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.adapter = gptAdapter

        chatRetrofit = ChatRetrofit(requireContext())
        chatService = chatRetrofit.chatService

        sendButton.setOnClickListener {
            val message = messageEditText.text.toString().trim()
            if (message.isNotEmpty()) {
                messageEditText.text.clear()

                // 서버로 메시지 전송
                sendMessage(requireContext(), message)
            }
        }
    }
    private fun sendMessage(context: Context, prompt: String) {
        val call = chatRetrofit.chatService.sendMessage(prompt)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val responseBodyString = response.body()
                    if (responseBodyString != null) {
                        // 서버에서 받은 응답을 Chat 객체로 만들어 SharedPreferences에 저장
                        val sharedPref = context.getSharedPreferences("gpt", Context.MODE_PRIVATE)
                        val editor = sharedPref?.edit()
                        editor?.putString("response_body", responseBodyString)
                        // 사용자 입력 메시지를 Chat 객체로 만들어 SharedPreferences에 저장
                        val userChat = Chat(prompt, true)
                        val json = Gson().toJson(userChat)
                        editor?.putString("chat", json)
                        editor?.apply()
                        Log.d("SharedPreferences", "Saved response: $responseBodyString")
                        // 화면에 새로운 채팅 추가
                        gptAdapter.addChat(userChat)
                        val botChat = Chat(responseBodyString, false)
                        gptAdapter.addChat(botChat)
                        recyclerView.scrollToPosition(gptAdapter.itemCount - 1)
                    }
                } else {
                    // handle error response
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                // handle failure
            }
        })
    }
}