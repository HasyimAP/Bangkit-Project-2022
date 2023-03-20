package com.fitverse.app.view.scanFavorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitverse.app.database.FavoriteFitnessEntity
import com.fitverse.app.databinding.FragmentFitnesFavBinding
import com.fitverse.app.model.FitnessModel
import com.fitverse.app.view.fitness.AdapterFitness


class FitnesFavFragment : Fragment() {

    private var _binding: FragmentFitnesFavBinding? = null
    private lateinit var adapterFavFitness: AdapterFitness
    private lateinit var favoriteFitnessViewModel: FavoriteFitnessViewModel
    val bundle = arguments

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFitnesFavBinding.inflate(inflater, container, false)

        adapterFavFitness = AdapterFitness()
        adapterFavFitness.notifyDataSetChanged()
        favoriteFitnessViewModel = ViewModelProvider(this)[FavoriteFitnessViewModel::class.java]

        binding.apply {
            recyleView.layoutManager = LinearLayoutManager(activity)
            recyleView.setHasFixedSize(true)
            recyleView.adapter = adapterFavFitness

        }
        favoriteFitnessViewModel.getFavorite()?.observe(viewLifecycleOwner) {
            if (it != null) {

                val listFavorite = mapList(it)
                adapterFavFitness.setList(listFavorite)
//                adapter.updateItems(listFavorite)
            }
        }
        return binding.root
    }

    private fun mapList(users: List<FavoriteFitnessEntity>): ArrayList<FitnessModel> {
        val listUsers = ArrayList<FitnessModel>()
        for (user in users) {
            val userMap = FitnessModel(
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