package com.example.jobhunt.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.jobhunt.R
import org.json.JSONObject

class TestJsonActivity : AppCompatActivity() {

    lateinit var textView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testjson)

        textView = findViewById(R.id.textView)

        val jsonData = applicationContext.resources.openRawResource(
            applicationContext.resources.getIdentifier(
                "news",
                "raw",
                applicationContext.packageName
            )
        ).bufferedReader().use{it.readText()}

        val outputJsonString = JSONObject(jsonData)

        val companyNames = outputJsonString.keys()

        for(companyName in companyNames) {
            val company = outputJsonString.getJSONObject(companyName)

            val state = company.getString("state")
            val content = company.getString("content")
            val position = company.getString("position")
            val plan = company.getString("plan")
            val link = company.getString("link")

            val previousData = textView.text

            var data:String = "$state\n$content\n$position\n$plan\n$link\n"

            textView.text = previousData.toString() + data
        }
    }
}