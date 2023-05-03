package com.example.jobhunt.Fragment

import RecentRecruitAdapter
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jobhunt.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobhunt.Adapter.NewComeRecruitAdapter
import com.example.jobhunt.Service.RecruitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class HomeFragment : Fragment() {

    private lateinit var recentRecruitAdapter: RecentRecruitAdapter
    private lateinit var newComeRecruitAdapter: NewComeRecruitAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)



        // Set up recent recruit recyclerview
        val recentRecruitRecyclerView = view.findViewById<RecyclerView>(R.id.rv_recentRecruit)
        recentRecruitRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recentRecruitAdapter = RecentRecruitAdapter()
        recentRecruitRecyclerView.adapter = recentRecruitAdapter

        // Set up new come recruit recyclerview
        val newComeRecruitRecyclerView = view.findViewById<RecyclerView>(R.id.rv_newComeRecruit)
        newComeRecruitRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        newComeRecruitAdapter = NewComeRecruitAdapter()
        newComeRecruitRecyclerView.adapter = newComeRecruitAdapter

        // Set up retrofit to make API call and fetch data
        val retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val recruitService = retrofit.create(RecruitService::class.java)



        GlobalScope.launch(Dispatchers.IO) {
            try {
                val recentRecruitResponse = recruitService.getRecruits()
                if (recentRecruitResponse.isSuccessful) {
                    val recentRecruitDataMap = recentRecruitResponse.body()
                    val recentRecruitDataList = recentRecruitDataMap?.values?.toList()
                    recentRecruitDataList?.let {
                        recentRecruitAdapter.setRecentRecruitDataList(it)
                    }
                } else {
                    Log.e(TAG, "Failed to fetch recent recruit data: ${recentRecruitResponse.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to fetch recent recruit data", e)
            }
        }

        // Fetch new come recruit data
        GlobalScope.launch(Dispatchers.IO) {
            val newComeRecruitResponse = recruitService.getRecruits()
            if (newComeRecruitResponse.isSuccessful) {
                val newComeRecruitDataList = newComeRecruitResponse.body()?.map { it.toObject() }
                newComeRecruitDataList?.let {
                    newComeRecruitAdapter.setNewComeRecruitDataList(it)
                }
            }
        }

        return view
    }

}