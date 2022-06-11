package com.fitverse.app.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fitverse.app.view.scanFavorite.FitnesFavFragment
import com.fitverse.app.view.scanFavorite.FoodFavFragment


class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FoodFavFragment()
            1 -> fragment = FitnesFavFragment()
        }
        return fragment as Fragment
    }
    override fun getItemCount(): Int {
        return 2
    }
}