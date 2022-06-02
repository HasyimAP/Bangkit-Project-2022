package com.fitverse.app.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitverse.app.model.UserModel
import com.fitverse.app.model.UserPreference
import kotlinx.coroutines.launch

class LoginViewModel(private val pref: UserPreference) : ViewModel() {

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(UserModel(user.id_user, user.email, user.pass, user.nama_user, user.jenis_kelamin, user.isLogin))
        }
    }
}