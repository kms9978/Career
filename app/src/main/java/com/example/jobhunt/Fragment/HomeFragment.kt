package com.example.jobhunt.Fragment

import RecentRecruitAdapter
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import com.example.jobhunt.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.jobhunt.Activity.BoardHomeActivity
import com.example.jobhunt.Adapter.NewComeRecruitAdapter
import com.example.jobhunt.Service.BookMarkService
import com.example.jobhunt.Service.RecruitService
import com.example.jobhunt.Utils.RetrofitClient
import com.example.jobhunt.DataModel.RecentRecruit
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class HomeFragment : Fragment() {

    private lateinit var recentRecruitAdapter: RecentRecruitAdapter
    private lateinit var newComeRecruitAdapter: NewComeRecruitAdapter
    private lateinit var recruitService: RecruitService
    private lateinit var bookmarkService: BookMarkService
    private lateinit var searchView: SearchView
    private lateinit var retrofitClient: RetrofitClient
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // RetrofitClient 초기화
        retrofitClient = RetrofitClient(requireContext())


        bookmarkService = retrofitClient.retrofitService



        val recentRecruitRecyclerView = view.findViewById<RecyclerView>(R.id.rv_recentRecruit)
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(recentRecruitRecyclerView)
        recentRecruitRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 3, GridLayoutManager.HORIZONTAL, false)
        recentRecruitAdapter = RecentRecruitAdapter(emptyList(), requireContext(), bookmarkService)
        recentRecruitRecyclerView.adapter = recentRecruitAdapter


        val newComeRecruitRecyclerView = view.findViewById<RecyclerView>(R.id.rv_newComeRecruit)
        newComeRecruitRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        newComeRecruitAdapter = NewComeRecruitAdapter()
        newComeRecruitRecyclerView.adapter = newComeRecruitAdapter


        val retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        recruitService = retrofit.create(RecruitService::class.java)


        val fab: ExtendedFloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener {
            // FAB 클릭시 수행될 코드 작성
            val intent = Intent(requireContext(), BoardHomeActivity::class.java)
            startActivity(intent)
        }



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


        fetchRecentRecruitData()


        fetchNewComeRecruitData()

        return view
    }



    private fun fetchRecentRecruitData() {
        try {
            recruitService.getRecruits().enqueue(object : Callback<Map<String, RecentRecruit>> {
                override fun onResponse(
                    call: Call<Map<String, RecentRecruit>>,
                    response: Response<Map<String, RecentRecruit>>
                ) {
                    if (response.isSuccessful) {
                        val recentRecruitDataList = response.body()?.map { entry ->
                            entry.value.apply { companyName = entry.key }
                        }?.toMutableList() ?: mutableListOf()
                        recentRecruitAdapter.setRecentRecruitDataList(recentRecruitDataList)
                        recentRecruitAdapter.filter.filter(searchView.query)
                    } else {
                        Log.e(ContentValues.TAG, "Failed to fetch recent recruit data: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Map<String, RecentRecruit>>, t: Throwable) {
                    Log.e(ContentValues.TAG, "Failed to fetch recent recruit data", t)
                }
            })
        } catch (e: Exception) {
            Log.e(ContentValues.TAG, "Failed to fetch recent recruit data", e)
        }
    }
    private fun fetchNewComeRecruitData() {
        try {
            recruitService.getRecruits().enqueue(object : Callback<Map<String, RecentRecruit>> {
                override fun onResponse(
                    call: Call<Map<String, RecentRecruit>>,
                    response: Response<Map<String, RecentRecruit>>
                ) {
                    if (response.isSuccessful) {
                        val newComeRecruitDataList = mutableListOf<RecentRecruit>()
                        response.body()?.forEach { (companyName, recentRecruit) ->
                            if (recentRecruit.position == "신입") {
                                recentRecruit.companyName = companyName
                                newComeRecruitDataList.add(recentRecruit)
                            }
                        }
                        newComeRecruitAdapter.setNewComeRecruitDataList(newComeRecruitDataList)
                    } else {
                        Log.e(ContentValues.TAG, "Failed to fetch recent recruit data: ${response.code()}")
                    }
                }
                override fun onFailure(call: Call<Map<String, RecentRecruit>>, t: Throwable) {
                    Log.e(TAG, "Failed to fetch new come recruit data", t)
                }
            })
        } catch (e: Exception) {
            Log.e(TAG, "Failed to fetch new come recruit data", e)
        }
    }
}