package com.fitverse.app.view.scanFavorite

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fitverse.app.database.FavoriteDatabase
import com.fitverse.app.database.FavoriteFitnessEntity
import com.fitverse.app.database.FavoriteUserDao
import com.fitverse.app.model.FitnessModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteFitnessViewModel(application: Application) : AndroidViewModel(application) {
        val user = MutableLiveData<FitnessModel>()

        private var userDao: FavoriteUserDao?
        private var userDb: FavoriteDatabase? = FavoriteDatabase.getDatabase(application)

        init {
            userDao = userDb?.favoriteUserDao()
        }

        fun addToFavorite(id: Int, name: String, photoUrl: String, description: String ) {
            CoroutineScope(Dispatchers.IO).launch {
                val fitness = FavoriteFitnessEntity(
                    id,
                    name,
                    photoUrl,
                    description
                )
                userDao?.addFavoriteFitness(fitness)
            }
        }

        suspend fun checkFavorite(id: Int) = userDao?.checkFavoriteFitness(id)

        fun deleteFromFavorite(id: Int) {
            CoroutineScope(Dispatchers.IO).launch {
                userDao?.deleteFavoriteFitness(id)
            }
        }
        fun getFavorite(): LiveData<List<FavoriteFitnessEntity>>? {
            return userDao?.getFavoriteFitness()
        }
}