package com.fitverse.app.view.profile

import androidx.lifecycle.*
import com.fitverse.app.model.UserModel
import com.fitverse.app.model.UserPreference
import kotlinx.coroutines.launch

class ProfileViewModel(private val pref: UserPreference) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is profile Fragment"
    }
    val text: LiveData<String> = _text

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}