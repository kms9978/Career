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

    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        favoriteAdapter = FavoriteAdapter(requireContext())
        view.findViewById<RecyclerView>(R.id.rv_bookmarkView).adapter = favoriteAdapter

        getBookmarks()

        return view
    }

    private fun getBookmarks() {
        val service = RetrofitClient.retrofitService
        val call = service.getBookmarks()

        call.enqueue(object : Callback<BookMarkListResponse> {
            override fun onResponse(
                call: Call<BookMarkListResponse>,
                response: Response<BookMarkListResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        favoriteAdapter.updateData(it.bookmark)
                    }
                } else {
                    Log.d("FavoriteFragment", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<BookMarkListResponse>, t: Throwable) {
                Log.d("FavoriteFragment", "Error: ${t.message}")
            }
        })
    }
}