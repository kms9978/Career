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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager



        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiYWIiLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjgzNDUxODM5fQ.gth72nIhqiqeSM6FfhYT64WtBqampa87OcxmCsxL6phPPxxn_DdX6IHqghI5VGKX3F0Fp2LX4uSN2XdmNv1gpA")
                    .build()
                chain.proceed(request)
            }
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://54.227.205.92:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val bookmarkService = retrofit.create(BookMarkService::class.java)

        try {
            val response = bookmarkService.getBookMarks().execute()
            if (response.isSuccessful) {
                val bookMarkList = response.body()?.bookmarks
                if (bookMarkList != null) {
                    favoriteAdapter.setData(bookMarkList)
                }
            } else {
                Log.d("FavoriteFragment", "Error: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.d("FavoriteFragment", "Error: ${e.message}")
        }
        return view
    }
}


