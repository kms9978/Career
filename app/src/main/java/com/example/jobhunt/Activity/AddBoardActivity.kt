package com.example.jobhunt.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.example.jobhunt.R
import com.example.jobhunt.Service.BoardService
import com.example.jobhunt.Utils.BoardRetrofit
import com.example.jobhunt.DataModel.BoardData
import com.example.jobhunt.DataModel.response.BoardResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddBoardActivity : AppCompatActivity() {
    private lateinit var edtName: EditText
    private lateinit var edtTopic: EditText
    private lateinit var edtTitle: EditText
    private lateinit var edtContent: EditText
    private lateinit var btnAddBoard: Button

    private lateinit var boardService: BoardService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addboard)

        val back_btn : ImageButton = findViewById(R.id.back_btn)
        // Initialize views
        edtName = findViewById(R.id.edt_name)
        edtTopic = findViewById(R.id.edt_topic)
        edtTitle = findViewById(R.id.edt_title)
        edtContent = findViewById(R.id.edt_content)
        btnAddBoard = findViewById(R.id.add_board)

        back_btn.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

        // Create an instance of BoardRetrofit and access the boardService
        val boardRetrofit = BoardRetrofit(this)
        boardService = boardRetrofit.boardService

        // Handle button click event
        btnAddBoard.setOnClickListener {
            val name = edtName.text.toString()
            val subject = edtTopic.text.toString()
            val title = edtTitle.text.toString()
            val content = edtContent.text.toString()

            // Create BoardData object
            val boardData = BoardData(0, content, "", subject, title, name)

            // Call the API to add board
            val call = boardService.addBoard(boardData)
            call.enqueue(object : Callback<BoardResponse> {
                override fun onResponse(call: Call<BoardResponse>, response: Response<BoardResponse>) {
                    if (response.isSuccessful) {
                        // Board added successfully
                        val boardResponse = response.body()
                        // Handle the response as needed
                    } else {
                        // API call failed
                        // Handle the error response
                    }
                }

                override fun onFailure(call: Call<BoardResponse>, t: Throwable) {
                    // API call failed
                    // Handle the error
                }
            })
        }
    }
}