package com.fitverse.app.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fitverse.app.model.UserModel
import com.fitverse.app.model.UserPreference
import kotlinx.coroutines.launch

class LoginViewModel(private val pref: UserPreference) : ViewModel() {

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(UserModel(user.id, user.name, user.token, user.isLogin))
        }
    }
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }
}