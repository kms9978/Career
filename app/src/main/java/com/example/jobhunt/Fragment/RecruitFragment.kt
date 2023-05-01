package com.example.jobhunt.Fragment

import android.content.ContentValues.TAG
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
import com.example.jobhunt.dataModel.CodenaryResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.jobhunt.dataModel.ResponseData
import com.google.gson.GsonBuilder


class RecruitFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var codenaryAdapter: CodenaryAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_recruit, container, false)

        // RecyclerView 초기화
        recyclerView = rootView.findViewById<RecyclerView>(R.id.Codenary_rc)

        // 레이아웃 매니저 초기화
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

        // 어댑터 초기화
        codenaryAdapter = CodenaryAdapter()
        recyclerView.adapter = codenaryAdapter

        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val codenaryService = retrofit.create(CodenaryService::class.java)
        codenaryService.getNews().enqueue(object : Callback<CodenaryResponse> {
            override fun onResponse(call: Call<CodenaryResponse>, response: Response<CodenaryResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    // response.body()가 CodenaryResponse
                    val data = response.body()?.data
                    if (data != null && data.isNotEmpty()) {
                        val dataList = mutableListOf<CodenaryData>()
                        for (item in data) {
                            val news = CodenaryData(
                                item.preview ?: "",
                                item.logo ?: "",
                                item.info ?: "",
                                item.date ?: ""
                            )
                            dataList.add(news)
                        }
                        codenaryAdapter.setData(dataList)
                    } else {
                        Log.e(TAG, "Data is empty or null")
                    }
                } else {
                    Log.e(TAG, "Failed to get data")
                }
            }

            override fun onFailure(call: Call<CodenaryResponse>, t: Throwable) {
                Log.e(TAG, "Failed to get data", t)
            }
        })
        return rootView
    }

    companion object {
        private const val TAG = "RecruitFragment"
    }
}