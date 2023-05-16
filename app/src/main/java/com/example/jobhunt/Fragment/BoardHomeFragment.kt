package com.example.jobhunt.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jobhunt.databinding.FragmentBoardHomeBinding


class BoardHomeFragment : Fragment() {
    lateinit var binding: FragmentBoardHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

}
