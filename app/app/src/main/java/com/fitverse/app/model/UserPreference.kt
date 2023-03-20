package com.fitverse.app.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


//class UserPreference constructor(context: Context) {
//    private val pref = context.getSharedPreferences("pref", Context.MODE)
//    private val userToken = "Token"
//    private val userIsLogin = "isLogin"
//
//    var token: String
//        set(value) {
//            pref.edit()
//                .putString(userToken, value)
//                .apply()
//        }
//        get() = pref.getString(userToken, "").toString()
//
//    var isLogin: Boolean
//        set(value) {
//            pref.edit()
//                .putBoolean(userIsLogin, value)
//                .apply()
//        }
//        get() = pref.getBoolean(userIsLogin, false)
//}


class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {
    private val THEME_KEY = booleanPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    fun getUser(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[USER_ID_KEY] ?:"",
                preferences[NAME_KEY] ?:"",
                preferences[TOKEN_KEY] ?:"",
                preferences[STATE_KEY] ?: false
            )
        }
    }

    suspend fun saveUser(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = user.id
            preferences[TOKEN_KEY] = user.token
            preferences[NAME_KEY] = user.name
            preferences[STATE_KEY] = user.isLogin
        }
    }

    suspend fun login() {
        dataStore.edit { preferences ->
            preferences[STATE_KEY] = true
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[STATE_KEY] = false
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null
        private val STATE_KEY = booleanPreferencesKey("isLogin")
        private val USER_ID_KEY = stringPreferencesKey("id")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val NAME_KEY = stringPreferencesKey("name")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}