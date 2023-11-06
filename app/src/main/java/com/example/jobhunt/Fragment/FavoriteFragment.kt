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
import com.example.jobhunt.Activity.MainActivity
import com.example.jobhunt.Adapter.FavoriteAdapter
import com.example.jobhunt.R
import com.example.jobhunt.Service.BookMarkService
import com.example.jobhunt.Utils.RetrofitClient
import com.example.jobhunt.Utils.TokenManager
import com.example.jobhunt.DataModel.response.BookMarkListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteFragment : Fragment() {

    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var bookmarkService: BookMarkService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        // Retrofit 및 BookMarkService 초기화
        bookmarkService = RetrofitClient(requireContext()).retrofitService
        favoriteAdapter = FavoriteAdapter(requireContext())

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_bookmarkView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = favoriteAdapter

        // TokenManager에서 token 값을 가져오는 부분
        val token = TokenManager(requireContext()).getToken()

        // 토큰이 없으면 로그인 화면으로 이동
        if (token.isNullOrEmpty()) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        } else {
            // Retrofit 요청 코드
            val call = bookmarkService.getBookmarks()

            // HTTP 요청을 비동기적으로 보내고 결과를 처리하는 Callback 등록
            call.enqueue(object : Callback<List<BookMarkListResponse>> {
                override fun onResponse(
                    call: Call<List<BookMarkListResponse>>,
                    response: Response<List<BookMarkListResponse>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { bookmarks ->
                            if (bookmarks.isNotEmpty()) {
                                // RecyclerView에 북마크 데이터를 업데이트한다.
                                favoriteAdapter.updateData(bookmarks[0].bookmark.toList(), token)
                            }
                        }
                    } else {
                        Log.d("FavoriteFragment", "Error: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<BookMarkListResponse>>, t: Throwable) {
                    Log.d("FavoriteFragment", "Error: ${t.message}")
                }
            })

        }

        return view
    }
}