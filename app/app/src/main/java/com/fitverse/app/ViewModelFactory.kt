package com.fitverse.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fitverse.app.model.UserPreference
import com.fitverse.app.view.fitness.FitnessListViewModel
import com.fitverse.app.view.fitness.fitnessScan.ScanFitnessResultViewModel
import com.fitverse.app.view.food.FoodListViewModel
import com.fitverse.app.view.food.foodScan.ScanFoodResultViewModel
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
            modelClass.isAssignableFrom(FoodListViewModel::class.java) -> {
                FoodListViewModel(pref) as T
            }
            modelClass.isAssignableFrom(FitnessListViewModel::class.java) -> {
                FitnessListViewModel(pref) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(pref) as T
            }
            modelClass.isAssignableFrom(ScanFoodResultViewModel::class.java) -> {
                ScanFoodResultViewModel(pref) as T
            }
            modelClass.isAssignableFrom(ScanFitnessResultViewModel::class.java) -> {
                ScanFitnessResultViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}