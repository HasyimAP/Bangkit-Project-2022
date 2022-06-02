package com.fitverse.app.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[USER_ID_KEY] ?:"",
                preferences[EMAIL_KEY] ?:"",
                preferences[PASS_KEY] ?:"",
                preferences[NAME_KEY] ?:"",
                preferences[JENIS_KELAMIN_KEY] ?:"",
                preferences[STATE_KEY] ?: false
            )
        }
    }

    suspend fun saveUser(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = user.id_user
            preferences[EMAIL_KEY] = user.email
            preferences[PASS_KEY] = user.pass
            preferences[NAME_KEY] = user.nama_user
            preferences[JENIS_KELAMIN_KEY] = user.jenis_kelamin
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

        private val USER_ID_KEY = stringPreferencesKey("id_user")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val PASS_KEY = stringPreferencesKey("pass")
        private val NAME_KEY = stringPreferencesKey("nama_user")
        private val JENIS_KELAMIN_KEY = stringPreferencesKey("jenis_kelamin")
        private val STATE_KEY = booleanPreferencesKey("state")


        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}