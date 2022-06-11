package com.fitverse.app.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitverse.app.database.FavoriteFitnessEntity
import com.fitverse.app.database.RecentScanEntity
import com.fitverse.app.databinding.FragmentFitnesFavBinding
import com.fitverse.app.databinding.FragmentHistoryBinding
import com.fitverse.app.model.FitnessModel
import com.fitverse.app.view.fitness.AdapterFitness
import com.fitverse.app.view.scanFavorite.FavoriteFitnessViewModel

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private lateinit var adapterFavFitness: AdapterFitness
    private lateinit var historyViewModel: HistoryViewModel
    val bundle = arguments

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        adapterFavFitness = AdapterFitness()
        adapterFavFitness.notifyDataSetChanged()
        historyViewModel = ViewModelProvider(this)[HistoryViewModel::class.java]

        binding.apply {
            recyleView.layoutManager = LinearLayoutManager(activity)
            recyleView.setHasFixedSize(true)
            recyleView.adapter = adapterFavFitness

        }
        historyViewModel.getFavorite()?.observe(viewLifecycleOwner) {
            if (it != null) {

                val listFavorite = mapList(it)
                adapterFavFitness.setList(listFavorite)
//                adapter.updateItems(listFavorite)
            }
        }
        return binding.root
    }

    private fun mapList(users: List<RecentScanEntity>): ArrayList<FitnessModel> {
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