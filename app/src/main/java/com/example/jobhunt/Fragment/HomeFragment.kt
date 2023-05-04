package com.example.jobhunt.Fragment

import RecentRecruitAdapter
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import com.example.jobhunt.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.jobhunt.Adapter.NewComeRecruitAdapter
import com.example.jobhunt.Service.BookMarkService
import com.example.jobhunt.Service.RecruitService
import com.example.jobhunt.dataModel.RecentRecruit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class HomeFragment : Fragment() {

    private lateinit var recentRecruitAdapter: RecentRecruitAdapter
    private lateinit var newComeRecruitAdapter: NewComeRecruitAdapter
    private lateinit var recruitService: RecruitService
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)


        // Set up recent recruit recyclerview
        val recentRecruitRecyclerView = view.findViewById<RecyclerView>(R.id.rv_recentRecruit)
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(recentRecruitRecyclerView)
        recentRecruitRecyclerView.layoutManager =
            GridLayoutManager(context, 3, GridLayoutManager.HORIZONTAL, false)
        recentRecruitAdapter = RecentRecruitAdapter(onBookmarkClick = { position ->
            // 북마크 버튼 클릭시 처리 로직 작성
            // 예시: Toast 메시지 출력
            Toast.makeText(context, "북마크가 클릭되었습니다. position: $position", Toast.LENGTH_SHORT).show()
        })
        recentRecruitRecyclerView.adapter = recentRecruitAdapter


        // Set up new come recruit recyclerview
        val newComeRecruitRecyclerView = view.findViewById<RecyclerView>(R.id.rv_newComeRecruit)
        newComeRecruitRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        newComeRecruitAdapter = NewComeRecruitAdapter()
        newComeRecruitRecyclerView.adapter = newComeRecruitAdapter

        // Set up retrofit to make API call and fetch data
        val retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        // Set up retrofit to make API call and fetch data

        recruitService = retrofit.create(RecruitService::class.java)

        // Set up search view
        searchView = view.findViewById<SearchView>(R.id.search_company)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                recentRecruitAdapter.filter.filter(newText)
                return true
            }
        })


        // Fetch recent recruit data
        fetchRecentRecruitData()

        // Fetch new come recruit data
        fetchNewComeRecruitData()

        return view
    }


    private fun fetchRecentRecruitData() {
        recruitService.getRecruits().enqueue(object : Callback<Map<String, RecentRecruit>> {
            override fun onResponse(
                call: Call<Map<String, RecentRecruit>>,
                response: Response<Map<String, RecentRecruit>>
            ) {
                if (response.isSuccessful) {
                    val recentRecruitDataList = response.body()?.values?.toList()
                    recentRecruitDataList?.let {
                        recentRecruitAdapter.filter.filter(searchView.query)
                    }
                } else {
                    Log.e(ContentValues.TAG, "Failed to fetch recent recruit data: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Map<String, RecentRecruit>>, t: Throwable) {
                Log.e(ContentValues.TAG, "Failed to fetch recent recruit data", t)
            }
        })
    }

    private fun fetchNewComeRecruitData() {
        recruitService.getRecruits().enqueue(object : Callback<Map<String, RecentRecruit>> {
            override fun onResponse(
                call: Call<Map<String, RecentRecruit>>,
                response: Response<Map<String, RecentRecruit>>
            ) {
                if (response.isSuccessful) {
                    val newComeRecruitDataList = response.body()?.values
                        ?.filter { it.position == "신입" }
                    newComeRecruitDataList?.let {
                        newComeRecruitAdapter.setNewComeRecruitDataList(it)
                    }
                }
            }

            override fun onFailure(call: Call<Map<String, RecentRecruit>>, t: Throwable) {
                Log.e(TAG, "Failed to fetch new come recruit data", t)
            }
        })
    }
}