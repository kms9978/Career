package com.example.jobhunt.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.jobhunt.Fragment.BoardHomeFragment
import com.example.jobhunt.Fragment.BoardListFragment


class ViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> BoardHomeFragment()
            else -> BoardListFragment()
        }
    }
}