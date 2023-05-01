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
import com.example.jobhunt.dataModel.CodenaryResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.jobhunt.dataModel.ResponseData


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

        // 데이터 로드 및 어댑터에 설정
        val retrofit = Retrofit.Builder()
            .baseUrl("https://withcareer.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val codenaryService = retrofit.create(CodenaryService::class.java)
        codenaryService.getNews().enqueue(object : Callback<CodenaryResponse> {
            override fun onResponse(call: Call<CodenaryResponse>, response: Response<CodenaryResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    // response.body()가 CodenaryResponse 타입으로 반환됩니다.
                    val data = response.body()?.dataList
                    if (data != null && data.isNotEmpty()) {
                        codenaryAdapter.setData(data)
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