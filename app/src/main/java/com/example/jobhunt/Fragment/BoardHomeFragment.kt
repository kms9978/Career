package com.example.jobhunt.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobhunt.Activity.AddBoardActivity
import com.example.jobhunt.Activity.BoardHomeActivity
import com.example.jobhunt.R
import com.example.jobhunt.databinding.FragmentBoardHomeBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class BoardHomeFragment : Fragment() {

    private lateinit var go_write_board: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_board_home, container, false)

        go_write_board = view.findViewById(R.id.go_write_board)
        recyclerView = view.findViewById(R.id.rv_boardList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        go_write_board.setOnClickListener{
            val intent = Intent(requireContext(), AddBoardActivity::class.java)
            startActivity(intent)
        }


        return view
    }

}
