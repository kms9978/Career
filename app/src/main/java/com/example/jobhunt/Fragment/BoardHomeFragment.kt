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
import com.example.jobhunt.Adapter.AddBoardAdapter
import com.example.jobhunt.R
import com.example.jobhunt.Service.BoardService
import com.example.jobhunt.Utils.BoardRetrofit
import com.example.jobhunt.DataModel.response.BoardListResponse
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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

        val boardRetrofit = BoardRetrofit(requireContext())


        boardService = boardRetrofit.boardService


        boardService.getBoard().enqueue(object : Callback<BoardListResponse> {
            override fun onResponse(call: Call<BoardListResponse>, response: Response<BoardListResponse>) {
                if (response.isSuccessful) {
                    val boardListResponse = response.body()
                    boardListResponse?.let { boardListResponse ->

                        val boardList = boardListResponse.boardList
                        adapter.setBoardList(boardList)
                    }
                } else {

                    val errorMessage = response.errorBody()?.string()
                    Log.e("BoardHomeFragment", "API call failed: $errorMessage")
                }
            }

            override fun onFailure(call: Call<BoardListResponse>, t: Throwable) {

                Log.e("BoardHomeFragment", "API call failed: ${t.message}")
            }
        })



        return view
    }
}




