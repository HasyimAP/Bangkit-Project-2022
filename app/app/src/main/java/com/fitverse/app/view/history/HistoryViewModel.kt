package com.fitverse.app.view.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fitverse.app.database.FavoriteDatabase
import com.fitverse.app.database.FavoriteFitnessEntity
import com.fitverse.app.database.FavoriteUserDao
import com.fitverse.app.database.RecentScanEntity
import com.fitverse.app.model.FitnessModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel (application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<FitnessModel>()

    private var userDao: FavoriteUserDao?
    private var userDb: FavoriteDatabase? = FavoriteDatabase.getDatabase(application)

    init {
        userDao = userDb?.favoriteUserDao()
    }

    fun addRecentScan(id: Int, name: String, photoUrl: String, description: String ) {
        CoroutineScope(Dispatchers.IO).launch {
            val fitness = RecentScanEntity(
                id,
                name,
                photoUrl,
                description
            )
            userDao?.addRecentScan(fitness)
        }
    }

    fun getFavorite(): LiveData<List<RecentScanEntity>>? {
        return userDao?.getRecentScan()
    }
}