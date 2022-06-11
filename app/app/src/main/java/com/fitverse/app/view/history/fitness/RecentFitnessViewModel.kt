package com.fitverse.app.view.history.fitness

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fitverse.app.database.FavoriteDatabase
import com.fitverse.app.database.FavoriteUserDao
import com.fitverse.app.database.RecentFitnessEntity
import com.fitverse.app.model.FitnessModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecentFitnessViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<FitnessModel>()

    private var userDao: FavoriteUserDao?
    private var userDb: FavoriteDatabase? = FavoriteDatabase.getDatabase(application)

    init {
        userDao = userDb?.favoriteUserDao()
    }

    fun addRecentFitness(id: Int, name: String, photoUrl: String, description: String ) {
        CoroutineScope(Dispatchers.IO).launch {
            val fitness = RecentFitnessEntity(
                id,
                name,
                photoUrl,
                description
            )
            userDao?.addRecentFitness(fitness)
        }
    }

    suspend fun checkRecentFitness(id: Int) = userDao?.checkRecentFitness(id)

    fun deleteFromRecentFitness(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.deleteRecentFitness(id)
        }
    }
    fun getRecentFitness(): LiveData<List<RecentFitnessEntity>>? {
        return userDao?.getRecentFitness()
    }
}