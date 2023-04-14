package com.example.jobhunt.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.jobhunt.R
import com.example.jobhunt.Activity.TestJsonActivity

class RecruitFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_recruit, container, false)
        val testButton = view.findViewById<Button>(R.id.testJson)
        testButton.setOnClickListener {
            val intent = Intent(activity, TestJsonActivity::class.java)
            startActivity(intent)
        }
        return view
    }


}