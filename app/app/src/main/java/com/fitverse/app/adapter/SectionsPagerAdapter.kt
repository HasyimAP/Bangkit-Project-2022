package com.fitverse.app.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fitverse.app.view.scanFavorite.FitnessFavFragment
import com.fitverse.app.view.scanFavorite.FoodFavFragment


class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FitnessFavFragment()
            1 -> fragment = FoodFavFragment()
        }
        return fragment as Fragment
    }
    override fun getItemCount(): Int {
        return 2
    }
}