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
import com.example.jobhunt.R
import com.example.jobhunt.Service.CodenaryService
import com.example.jobhunt.dataModel.CodenaryData

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecruitFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var codenaryAdapter: CodenaryAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var newsList: CodenaryData

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
        newsList = CodenaryData("", "", "", "") // newsList 초기화
        codenaryAdapter = CodenaryAdapter(newsList)
        recyclerView.adapter = codenaryAdapter

        // 데이터 로드 및 어댑터에 설정
        val retrofit = Retrofit.Builder()
            .baseUrl("https://github.com/withcareer/backend_jobhunt/blob/98d10c3f13faf884ba249ae58a2bac9dbdac6f1e/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val codenaryService = retrofit.create(CodenaryService::class.java)
        codenaryService.getNews().enqueue(object : Callback<CodenaryData> {
            override fun onResponse(call: Call<CodenaryData>, response: Response<CodenaryData>) {
                if (response.isSuccessful) {
                    val newsList = response.body()
                    if (newsList != null) {
                        codenaryAdapter.setData(newsList)
                    } else {
                        Log.e("RecruitFragment", "Failed to get data")
                    }
                } else {
                    Log.e("RecruitFragment", "Failed to get data")
                }
            }

            override fun onFailure(call: Call<CodenaryData>, t: Throwable) {
                Log.e("RecruitFragment", "Failed to get data", t)
            }
        })

        return rootView
    }
}
