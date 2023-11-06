package com.example.jobhunt.Fragment

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobhunt.Adapter.CodenaryAdapter
import com.example.jobhunt.Activity.DetailcodenaryActivity
import com.example.jobhunt.R
import com.example.jobhunt.Service.CodenaryService
import com.example.jobhunt.DataModel.CodenaryData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.google.gson.GsonBuilder


class RecruitFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var codenaryAdapter: CodenaryAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 레이아웃 파일 연결
        val rootView = inflater.inflate(R.layout.fragment_recruit, container, false)

        // RecyclerView 초기화
        recyclerView = rootView.findViewById<RecyclerView>(R.id.Codenary_rc)

        // 레이아웃 매니저 초기화
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

        // 어댑터 초기화
        codenaryAdapter = CodenaryAdapter()
        recyclerView.adapter = codenaryAdapter

        // Retrofit 객체 생성
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        // CodenaryService 인터페이스 구현체 생성
        val codenaryService = retrofit.create(CodenaryService::class.java)

        // RecyclerView 아이템 클릭 리스너 설정
        codenaryAdapter.setOnItemClickListener(object : CodenaryAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                // 선택한 아이템의 데이터 가져오기
                val item = codenaryAdapter.dataList[position]

                // DetailcodenaryActivity로 데이터 전달하기
                val intent = Intent(context, DetailcodenaryActivity::class.java)
                intent.putExtra("codenary_data", item)
                startActivity(intent)
            }
        })

        // 데이터 읽어오기
        codenaryService.getNews().enqueue(object : Callback<Map<String, CodenaryData>?> {
            override fun onResponse(
                call: Call<Map<String, CodenaryData>?>,
                response: Response<Map<String, CodenaryData>?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    // 데이터가 있는 경우 어댑터에 전달
                    val data = response.body()!!.values.toList()
                    if (data.isNotEmpty()) {
                        val dataList = mutableListOf<CodenaryData>()
                        for (item in data) {
                            val news = CodenaryData(
                                item.title ?: "",
                                item.logo ?: "",
                                item.info ?: "",
                                item.date ?: ""
                            )
                            dataList.add(news)
                        }
                        codenaryAdapter.setData(dataList)
                    } else {
                        Log.e(TAG, "데이터가 비어있거나 Null: ${response.message()}")
                    }
                } else {
                    Log.e(TAG, "get Data 실패")
                }
            }

            override fun onFailure(call: Call<Map<String, CodenaryData>?>, t: Throwable) {
                Log.e(TAG, "get Data 실패2", t)
            }
        })

        return rootView
    }
}