package com.fitverse.app.view.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.fitverse.app.databinding.FragmentDashboardBinding
import com.fitverse.app.view.comingSoon.ComingSoonActivity
import com.fitverse.app.view.fitness.FitnessListActivity
import com.fitverse.app.view.fitness.fitnessScan.ScanFitnessActivity
import com.fitverse.app.view.food.FoodListActivity
import com.fitverse.app.view.food.foodScan.ScanFoodActivity
import com.fitverse.app.view.main.MainViewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val viewModel by activityViewModels<MainViewModel>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel.getUser().observe(requireActivity()) { user ->
                "Halo, ${user.name}!".also { nama.text = it }
            }

            scanFoodButton.setOnClickListener{
                Intent(requireActivity(), ScanFoodActivity::class.java).apply {
                    startActivity(this)
                }
            }
            foodListButton.setOnClickListener{
                Intent(requireActivity(), FoodListActivity::class.java).apply {
                    startActivity(this)
                }
            }
            foodNewsButton.setOnClickListener{
                comingSoon()
            }
            scanFitnessButton.setOnClickListener{
                Intent(requireActivity(), ScanFitnessActivity::class.java).apply {
                    startActivity(this)
                }
            }
            exerciseListButton.setOnClickListener{
                Intent(requireActivity(), FitnessListActivity::class.java).apply {
                    startActivity(this)
                }
            }
            fitnessNewsButton.setOnClickListener{
                comingSoon()
            }
        }
    }

    private fun comingSoon() {
        Intent(requireActivity(), ComingSoonActivity::class.java).apply {
            startActivity(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}