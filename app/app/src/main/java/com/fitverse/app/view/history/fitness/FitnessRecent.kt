package com.fitverse.app.view.history.fitness

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitverse.app.database.RecentFitnessEntity
import com.fitverse.app.databinding.FragmentFitnessRecentBinding
import com.fitverse.app.model.FitnessModel
import com.fitverse.app.view.fitness.AdapterFitness

class FitnessRecent : Fragment() {

    private var _binding: FragmentFitnessRecentBinding? = null
    private lateinit var adapterRecentFitness: AdapterRecentFitness
    private lateinit var recentFitnessViewModel: RecentFitnessViewModel
    val bundle = arguments

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFitnessRecentBinding.inflate(inflater, container, false)

        adapterRecentFitness = AdapterRecentFitness()
        adapterRecentFitness.notifyDataSetChanged()
        recentFitnessViewModel = ViewModelProvider(this)[RecentFitnessViewModel::class.java]

        binding.apply {
            recyleView.layoutManager = LinearLayoutManager(activity)
            recyleView.setHasFixedSize(true)
            recyleView.adapter = adapterRecentFitness

        }
        recentFitnessViewModel.getRecentFitness()?.observe(viewLifecycleOwner) {
            if (it != null) {

                val listRecentFitness = mapList(it)
                adapterRecentFitness.setList(listRecentFitness)
            }
        }
        return binding.root
    }

    private fun mapList(users: List<RecentFitnessEntity>): ArrayList<FitnessModel> {
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