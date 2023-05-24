package com.example.jobhunt.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobhunt.Activity.AddBoardActivity
import com.example.jobhunt.Activity.DetailcodenaryActivity
import com.example.jobhunt.Adapter.AddBoardAdapter
import com.example.jobhunt.Adapter.CodenaryAdapter
import com.example.jobhunt.R
import com.example.jobhunt.Service.BoardService
import com.example.jobhunt.Settings.BoardRetrofit
import com.example.jobhunt.dataModel.BoardListResponse
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class BoardHomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AddBoardAdapter
    private lateinit var boardService: BoardService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_board_home, container, false)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.rv_boardList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = AddBoardAdapter()
        recyclerView.adapter = adapter

        val goWriteBoard: FloatingActionButton = view.findViewById(R.id.go_write_board)
        goWriteBoard.setOnClickListener{
            val intent = Intent(requireContext(), AddBoardActivity::class.java)
            startActivity(intent)
        }

        // Create an instance of BoardRetrofit passing the context
        val boardRetrofit = BoardRetrofit(requireContext())

        // Access the boardService from BoardRetrofit
        boardService = boardRetrofit.boardService

        // Call the API to get board list
        boardService.getBoard().enqueue(object : Callback<BoardListResponse> {
            override fun onResponse(call: Call<BoardListResponse>, response: Response<BoardListResponse>) {
                if (response.isSuccessful) {
                    val boardListResponse = response.body()
                    boardListResponse?.let { boardListResponse ->
                        // Update adapter data
                        val boardList = boardListResponse.boardList
                        adapter.setBoardList(boardList)
                    }
                } else {
                    // API call failed
                    // Handle the error response
                    val errorMessage = response.errorBody()?.string()
                    Log.e("BoardHomeFragment", "API call failed: $errorMessage")
                }
            }

            override fun onFailure(call: Call<BoardListResponse>, t: Throwable) {
                // API call failed
                // Handle the error
                Log.e("BoardHomeFragment", "API call failed: ${t.message}")
            }
        })



        return view
    }
}




