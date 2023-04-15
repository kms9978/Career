package com.example.jobhunt.Fragment

import RecentRecruitAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobhunt.R
import com.example.jobhunt.data.RecentRecruit
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recentRecruitList: MutableList<RecentRecruit>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.rv_recentRecruit)
        recyclerView.layoutManager = LinearLayoutManager(context)

        recentRecruitList = readRecentRecruitData()
        recyclerView.adapter = RecentRecruitAdapter(recentRecruitList)

        return view
    }

    private fun readRecentRecruitData(): MutableList<RecentRecruit> {
        val recentRecruitList = mutableListOf<RecentRecruit>()

        try {
            val inputStream: InputStream = resources.openRawResource(R.raw.news)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(jsonString)

            for (key in jsonObject.keys()) {
                val innerJsonObject = jsonObject.getJSONObject(key)

                val companyName = key
                val content = innerJsonObject.getString("content")

                recentRecruitList.add(RecentRecruit(companyName, content))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return recentRecruitList
    }
}