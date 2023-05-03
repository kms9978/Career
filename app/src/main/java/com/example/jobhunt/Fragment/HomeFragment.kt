package com.example.jobhunt.Fragment

import RecentRecruitAdapter
import android.R
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobhunt.Adapter.NewComeRecruitAdapter
import com.example.jobhunt.Service.RecruitService
import com.example.jobhunt.dataModel.CodenaryData
import com.example.jobhunt.dataModel.RecentRecruit
import com.example.jobhunt.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recentRecruitAdapter: RecentRecruitAdapter
    private lateinit var newComeRecruitAdapter: NewComeRecruitAdapter
    private lateinit var retrofit: Retrofit
    private lateinit var recruitService: RecruitService
    private var recentRecruits = mutableListOf<RecentRecruit>()
    private var newComeRecruits = mutableListOf<RecentRecruit>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupRetrofit()
        setupRecyclerView()
        setupSearchView()
        fetchData()

        return binding.root
    }

    private fun setupRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        recruitService = retrofit.create(RecruitService::class.java)
    }

    private fun setupRecyclerView() {
        recentRecruitAdapter = RecentRecruitAdapter(mutableListOf())
        binding.rvRecentRecruit.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = recentRecruitAdapter
        }

        newComeRecruitAdapter = NewComeRecruitAdapter(mutableListOf())
        binding.rvNewComeRecruit.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newComeRecruitAdapter
        }
    }

        private fun setupSearchView() {
            binding.searchCompany.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    recentRecruitAdapter.filter.filter(newText)
                    return false
                }
            })
        }

        private fun fetchData() {
            recruitService.getRecruits().enqueue(object : Callback<Map<String, RecentRecruit>> {
                override fun onResponse(
                    call: Call<Map<String, RecentRecruit>>,
                    response: Response<Map<String, RecentRecruit>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        // 데이터가 있는 경우 어댑터에 전달
                        val data = response.body()!!.values.toList()
                        if (data.isNotEmpty()) {
                            val dataList = mutableListOf<RecentRecruit>()
                            for (item in data) {
                                val news = RecentRecruit(
                                    item.companyName ?: "",
                                    item.content ?: "",
                                    item.position ?: "",
                                    item.plan ?: "",
                                    item.url ?: "",
                                    item.imgUrl ?: ""
                                )
                                dataList.add(news)
                            }
                            RecentRecruitAdapter.setData(dataList)
                        } else {
                            Log.e(TAG, "데이터가 비어있거나 Null: ${response.message()}")
                        }
                    } else {
                        Log.e(TAG, "get Data 실패")
                    }
                }

                override fun onFailure(call: Call<Map<String, RecentRecruit>?>, t: Throwable) {
                    Log.e(TAG, "get Data 실패2", t)
                }
            })

        }
    }
