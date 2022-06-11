package com.fitverse.app.view.scanFavorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitverse.app.database.FavoriteFoodEntity
import com.fitverse.app.databinding.FragmentFoodFavBinding
import com.fitverse.app.model.FoodModel
import com.fitverse.app.view.food.AdapterFood

class FoodFavFragment : Fragment() {

    private var _binding: FragmentFoodFavBinding? = null
    private lateinit var adapterFavFood: AdapterFood
    private lateinit var favoriteFoodViewModel: FavoriteFoodViewModel
    val bundle = arguments

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodFavBinding.inflate(inflater, container, false)

        adapterFavFood = AdapterFood()
        adapterFavFood.notifyDataSetChanged()
        favoriteFoodViewModel = ViewModelProvider(this)[FavoriteFoodViewModel::class.java]

        binding.apply {
            recyleView.layoutManager = LinearLayoutManager(activity)
            recyleView.setHasFixedSize(true)
            recyleView.adapter = adapterFavFood

        }
        favoriteFoodViewModel.getFavorite()?.observe(viewLifecycleOwner) {
            if (it != null) {

                val listFavorite = mapList(it)
                adapterFavFood.setList(listFavorite)
            }
        }
        return binding.root
    }

    private fun mapList(users: List<FavoriteFoodEntity>): ArrayList<FoodModel> {
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