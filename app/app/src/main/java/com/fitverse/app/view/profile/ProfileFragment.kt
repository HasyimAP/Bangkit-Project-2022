package com.fitverse.app.view.profile

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fitverse.app.R
import com.fitverse.app.databinding.FragmentProfileBinding
import com.fitverse.app.view.food.ScanFoodActivity
import com.fitverse.app.view.scanFavorite.ScanFavoriteActivity


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.apply {
            favButton.setOnClickListener {
                Intent(requireActivity(), ScanFavoriteActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        return binding.root


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