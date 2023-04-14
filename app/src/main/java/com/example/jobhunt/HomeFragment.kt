package com.example.jobhunt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobhunt.data.RecentRecruitData
import com.example.jobhunt.model.RecentRecruitAdapter

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var recentRecruitAdapter : RecentRecruitAdapter
    private val datas = mutableListOf<RecentRecruitData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rv_recentRecruit)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recentRecruitAdapter = RecentRecruitAdapter(this)
        recyclerView.adapter = recentRecruitAdapter

        datas.apply {
            add(RecentRecruitData(name="테스트1", title = "테스트입니다1"))
            add(RecentRecruitData(name="테스트2", title = "테스트입니다2"))
            add(RecentRecruitData(name="테스트3", title = "테스트입니다3"))
            add(RecentRecruitData(name="테스트4", title = "테스트입니다4"))
            add(RecentRecruitData(name="테스트5", title = "테스트입니다5"))
        }

        recentRecruitAdapter.datas = datas
        recentRecruitAdapter.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}