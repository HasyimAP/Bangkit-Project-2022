package com.fitverse.app.view.history.food

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitverse.app.database.RecentFoodEntity
import com.fitverse.app.databinding.FragmentFoodRecentBinding
import com.fitverse.app.model.FoodModel

class FoodRecent : Fragment() {

    private var _binding: FragmentFoodRecentBinding? = null
    private lateinit var adapterRecentFood: AdapterRecentFood
    private lateinit var recentFoodViewModel: RecentFoodViewModel
    val bundle = arguments

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodRecentBinding.inflate(inflater, container, false)

        adapterRecentFood = AdapterRecentFood()
        adapterRecentFood.notifyDataSetChanged()
        recentFoodViewModel = ViewModelProvider(this)[RecentFoodViewModel::class.java]

        binding.apply {
            recyleView.layoutManager = LinearLayoutManager(activity)
            recyleView.setHasFixedSize(true)
            recyleView.adapter = adapterRecentFood

        }
        recentFoodViewModel.getRecentFood()?.observe(viewLifecycleOwner) {
            if (it != null) {

                val listRecentFood = mapList(it)
                adapterRecentFood.setList(listRecentFood)
            }
        }
        return binding.root
    }

    private fun mapList(users: List<RecentFoodEntity>): ArrayList<FoodModel> {
        val listUsers = ArrayList<FoodModel>()
        for (user in users) {
            val userMap = FoodModel(
                user.id,
                user.name,
                user.photoUrl,
                user.description
            )
            listUsers.add(userMap)
        }
        return listUsers
    }

    fun deleteItems() {
        recentFoodViewModel.deleteFromRecentFood(id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}