package com.fitverse.app.view.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fitverse.app.databinding.FragmentDashboardBinding
import com.fitverse.app.view.comingSoon.ComingSoonActivity
import com.fitverse.app.view.fitness.ScanFitnessActivity
import com.fitverse.app.view.food.FoodListActivity
import com.fitverse.app.view.food.ScanFoodActivity
import com.fitverse.app.view.main.MainActivity

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
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