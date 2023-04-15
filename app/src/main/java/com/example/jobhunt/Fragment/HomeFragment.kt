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
import com.example.jobhunt.dataModel.RecentRecruit
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class HomeFragment : Fragment() {

    // Declare properties for the RecyclerView and list of recent recruit items
    private lateinit var recyclerView: RecyclerView
    private lateinit var recentRecruitList: MutableList<RecentRecruit>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // fragment_home.xml 레이아웃을 inflate하여 view 객체를 생성한다
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // 리사이클러뷰 초기화
        recyclerView = view.findViewById(R.id.rv_recentRecruit)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // 최근 채용 정보 데이터를 읽어온다
        recentRecruitList = readRecentRecruitData()
        // 어댑터를 리사이클러뷰에 설정한다
        recyclerView.adapter = RecentRecruitAdapter(recentRecruitList)


        return view
    }


    // 최근 채용 정보 데이터를 읽어오는 함수
    private fun readRecentRecruitData(): MutableList<RecentRecruit> {
        // 최근 채용 정보 데이터 리스트 생성
        val recentRecruitList = mutableListOf<RecentRecruit>()

        try {
            // 앱 리소스에 있는 raw/news.json 파일을 읽어들인다
            val inputStream: InputStream = resources.openRawResource(R.raw.news)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(jsonString)

            // JSON 객체를 반복하며 최근 채용 정보 데이터를 파싱하여 리스트에 추가한다
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