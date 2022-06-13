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

    private fun mapList(fitness: List<RecentFitnessEntity>): ArrayList<FitnessModel> {
        val listFitness = ArrayList<FitnessModel>()
        for (fit in fitness) {
            val fitnessMap = FitnessModel(
                fit.id,
                fit.name,
                fit.photoUrl,
                fit.description
            )
            listFitness.add(fitnessMap)
        }
        return listFitness
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