package com.fitverse.app.view.scanFavorite

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fitverse.app.database.FavoriteDatabase
import com.fitverse.app.database.FavoriteFoodEntity
import com.fitverse.app.database.FavoriteUserDao
import com.fitverse.app.model.FoodModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteFoodViewModel(application: Application) : AndroidViewModel(application) {
        val user = MutableLiveData<FoodModel>()

        private var userDao: FavoriteUserDao?
        private var userDb: FavoriteDatabase? = FavoriteDatabase.getDatabase(application)

        init {
            userDao = userDb?.favoriteUserDao()
        }

        fun addToFavorite(id: Int, name: String, photoUrl: String, description: String ) {
            CoroutineScope(Dispatchers.IO).launch {
                val food = FavoriteFoodEntity(
                    id,
                    name,
                    photoUrl,
                    description
                )
                userDao?.addFavoriteFood(food)
            }
        }

        suspend fun checkFavorite(id: Int) = userDao?.checkFavoriteFood(id)

        fun deleteFromFavorite(id: Int) {
            CoroutineScope(Dispatchers.IO).launch {
                userDao?.deleteFavoriteFood(id)
            }
        }
    fun getFavorite(): LiveData<List<FavoriteFoodEntity>>? {
        return userDao?.getFavoriteFood()
    }
}