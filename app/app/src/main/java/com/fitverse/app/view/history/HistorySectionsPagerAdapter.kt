package com.fitverse.app.view.history

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fitverse.app.view.history.fitness.FitnessRecent
import com.fitverse.app.view.history.food.FoodRecent

class HistorySectionsPagerAdapter(activity: HistoryFragment) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FoodRecent()
            1 -> fragment = FitnessRecent()
        }
        return fragment as Fragment
    }
    override fun getItemCount(): Int {
        return 2
    }
}