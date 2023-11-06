package com.example.jobhunt.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jobhunt.databinding.FragmentBoardHomeBinding
import com.example.jobhunt.databinding.FragmentBoardListBinding


class BoardListFragment : Fragment() {
    lateinit var binding: FragmentBoardListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardListBinding.inflate(inflater, container, false)

        return binding.root
    }

}
