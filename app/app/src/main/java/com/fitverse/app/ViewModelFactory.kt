package com.fitverse.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fitverse.app.model.UserPreference
import com.fitverse.app.view.food.ListViewModel
import com.fitverse.app.view.login.LoginViewModel
import com.fitverse.app.view.main.MainViewModel
import com.fitverse.app.view.profile.ProfileViewModel


class ViewModelFactory(private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            modelClass.isAssignableFrom(ListViewModel::class.java) -> {
                ListViewModel(pref) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}