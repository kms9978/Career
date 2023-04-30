package com.example.jobhunt.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobhunt.Adapter.CodenaryAdapter
import com.example.jobhunt.R
import com.example.jobhunt.Service.CodenaryService
import com.example.jobhunt.dataModel.CodenaryData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecruitFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var codenaryAdapter: CodenaryAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var newsList: List<CodenaryData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_recruit, container, false)

        // RecyclerView 초기화
        recyclerView = rootView.findViewById<RecyclerView>(R.id.Codenary_rc)

        // 레이아웃 매니저 초기화
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

        // 어댑터 초기화
        newsList = emptyList() // newsList 초기화
        codenaryAdapter = CodenaryAdapter(newsList) // lateinit으로 선언한 codenaryAdapter 초기화
        recyclerView.adapter = codenaryAdapter

        // 데이터 로드 및 어댑터에 설정
        val retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/withcareer/backend_jobhunt/98d10c3f13faf884ba249ae58a2bac9dbdac6f1e/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val codenaryService = retrofit.create(CodenaryService::class.java)
        codenaryService.getNews().enqueue(object : retrofit2.Callback<List<CodenaryData>> {
            override fun onResponse(
                call: Call<List<CodenaryData>>,
                response: Response<List<CodenaryData>>
            ) {
                if (response.isSuccessful) {
                    val newsList = response.body() ?: emptyList()
                    codenaryAdapter.setData(newsList)
                } else {
                    Log.e("RecruitFragment", "Failed to get data")
                }
            }

            override fun onFailure(call: Call<List<CodenaryData>>, t: Throwable) {
                Log.e("RecruitFragment", "Failed to get data", t)
            }
        })

        return rootView
    }
}