package com.example.jobhunt.Fragment

import android.content.ContentValues.TAG
import android.content.Context
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

import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset


class RecruitFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var codenaryAdapter: CodenaryAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        // 데이터 읽어오기
        codenaryService.getNews().enqueue(object : Callback<CodenaryResponse> {
            override fun onResponse(
                call: Call<CodenaryResponse>,
                response: Response<CodenaryResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()?.data ?: emptyList()
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
                        Log.e(TAG, "Data is empty or null: ${response.message()}")
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

    private fun getDataList(): List<CodenaryData> {
        val jsonStr = getJsonDataFromAsset(requireActivity(), "codenary.json")
        return parseJsonData(jsonStr)
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    private fun parseJsonData(jsonStr: String?): List<CodenaryData> {
        val dataList = mutableListOf<CodenaryData>()
        try {
            val jsonObj = JSONObject(jsonStr)
            val jsonArray = jsonObj.getJSONArray("data")
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val preview = obj.getString("preview")
                val logo = obj.getString("logo")
                val info = obj.getString("info")
                val date = obj.getString("date")
                val data = CodenaryData(preview, logo, info, date)
                dataList.add(data)
            }
        } catch (e: JSONException) {
            Log.e(TAG, "Failed to parse JSON data", e)
        }
        return dataList
    }

    private fun readJsonDataFromAsset(): String? {
        var json: String? = null
        try {
            val inputStream = requireContext().assets.open("codenary_data.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charset.defaultCharset())
        } catch (e: IOException) {
            Log.e(TAG, "Failed to read JSON data from asset file", e)
        }
        return json
    }

    private fun loadData() {
        val jsonStr = readJsonDataFromAsset()
        if (jsonStr != null) {
            val dataList = parseJsonData(jsonStr)
            codenaryAdapter.setData(dataList)
        } else {
            Log.e(TAG, "Failed to load data")
        }
    }
}