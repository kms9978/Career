package com.example.jobhunt.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobhunt.Adapter.CodenaryAdapter
import com.example.jobhunt.Adapter.FavoriteAdapter
import com.example.jobhunt.R
import com.example.jobhunt.Service.BookMarkService
import com.example.jobhunt.dataModel.BookMarkData
import com.example.jobhunt.dataModel.BookMarkListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FavoriteFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        // RecyclerView와 Adapter 초기화
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_bookmarkView)
        favoriteAdapter = FavoriteAdapter(requireContext(), emptyList())
        recyclerView.adapter = favoriteAdapter

        // Retrofit을 사용하여 서버에서 북마크 정보를 가져옵니다.
        val retrofit = Retrofit.Builder()
            .baseUrl("http://54.227.205.92:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val bookmarkService = retrofit.create(BookMarkService::class.java)

        bookmarkService.getBookMarks().enqueue(object : Callback<BookMarkListResponse> {
            override fun onResponse(call: Call<BookMarkListResponse>, response: Response<BookMarkListResponse>) {
                if (response.isSuccessful) {
                    val bookmarkListResponse = response.body()
                    favoriteAdapter.setData(bookmarkListResponse?.data ?: emptyList()) // BookMarkListResponse의 data 필드에 저장된 리스트를 Adapter에 전달합니다.
                } else {
                    Log.e("FavoriteFragment", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<BookMarkListResponse>, t: Throwable) {
                Log.e("FavoriteFragment", "Error: ${t.message}")
            }
        })

        return view
    }
}