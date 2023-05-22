package com.example.jobhunt.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobhunt.Activity.AddBoardActivity
import com.example.jobhunt.Adapter.AddBoardAdapter
import com.example.jobhunt.R
import com.example.jobhunt.Service.BoardService
import com.example.jobhunt.dataModel.BoardListResponse
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

        val go_write_board : FloatingActionButton
        go_write_board = view.findViewById(R.id.go_write_board)
        go_write_board.setOnClickListener{
            val intent = Intent(requireContext(), AddBoardActivity::class.java)
            startActivity(intent)
        }


        // Set up Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://54.227.205.92:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        boardService = retrofit.create(BoardService::class.java)

        // Call the API to get board list
        val call = boardService.getBoard()
        call.enqueue(object : Callback<BoardListResponse> {
            override fun onResponse(call: Call<BoardListResponse>, response: Response<BoardListResponse>) {
                if (response.isSuccessful) {
                    val boardListResponse = response.body()
                    boardListResponse?.let {
                        // Update adapter data
                        adapter.setBoardList(it.bookmark)
                    }
                } else {
                    // API call failed
                    // Handle the error response
                }
            }

            override fun onFailure(call: Call<BoardListResponse>, t: Throwable) {
                // API call failed
                // Handle the error
            }
        })

        return view
    }
}