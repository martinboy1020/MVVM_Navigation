package com.example.mvvm_navigation.ui.main.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class RecentMatchViewPager2Adapter(fragment: Fragment, fragmentList: ArrayList<String>) : FragmentStateAdapter(fragment) {

    private val tabFragmentCreators: Map<Int, () -> Fragment> = mapOf()

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return RecentMatchFragment(position)
    }

}