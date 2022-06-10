package com.fitverse.app.view.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fitverse.app.ViewModelFactory
import com.fitverse.app.databinding.FragmentProfileBinding
import com.fitverse.app.model.UserPreference
import com.fitverse.app.view.dashboard.DashboardViewModel
import com.fitverse.app.view.main.MainActivity
import com.fitverse.app.view.main.MainViewModel
import com.fitverse.app.view.scanFavorite.ScanFavoriteActivity


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
//    private lateinit var profileViewModel: MainViewModel
//    private lateinit var profileViewModel: ProfileViewModel
    val bundle = arguments
    val namaUser = bundle?.getString("nama_user")

    private val binding get() = _binding!!
//    val pref = UserPreference.getInstance(dataStore)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val pref = UserPreference.getInstance(dataStore)
//    profileViewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
//        profileViewModel =  ViewModelProvider(requireActivity(), ViewModelFactory(pref))[MainViewModel::class.java]

        _binding = FragmentProfileBinding.inflate(inflater, container, false)


        binding.apply {
            favButton.setOnClickListener {
                Intent(requireActivity(), ScanFavoriteActivity::class.java).apply {
                    startActivity(this)
                }
            }
            nama.text = namaUser
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