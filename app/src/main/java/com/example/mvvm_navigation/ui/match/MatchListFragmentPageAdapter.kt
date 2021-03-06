package com.example.mvvm_navigation.ui.match

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mvvm_navigation.ui.match.matchlist.MatchListFragment
import com.example.mvvm_navigation.utils.GameStatusUtils

class MatchListFragmentPageAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    var fragments: ArrayList<Fragment> = arrayListOf(
        MatchListFragment(MatchListFragment.MATCH_ING),
        MatchListFragment(MatchListFragment.MATCH_UNOPEN),
        MatchListFragment(MatchListFragment.MATCH_ENDING),
        MatchListFragment(MatchListFragment.MATCH_TOP)
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}