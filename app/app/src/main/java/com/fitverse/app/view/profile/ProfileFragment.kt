package com.fitverse.app.view.profile

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.fitverse.app.databinding.FragmentProfileBinding
import com.fitverse.app.view.main.MainViewModel
import com.fitverse.app.view.scanFavorite.ScanFavoriteActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val profileViewModel by activityViewModels<MainViewModel>()


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)


        binding.apply {
            favButton.setOnClickListener {
                Intent(requireActivity(), ScanFavoriteActivity::class.java).apply {
                    startActivity(this)
                }
            }
            profileViewModel.getUser().observe(requireActivity()) { user ->
                nama.text = user.name
            }
//            outButton.setOnClickListener {
//                profileViewModel.logout()
//            }
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