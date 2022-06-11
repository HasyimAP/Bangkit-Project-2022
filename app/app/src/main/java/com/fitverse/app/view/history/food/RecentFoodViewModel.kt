package com.fitverse.app.view.history.food

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fitverse.app.database.FavoriteDatabase
import com.fitverse.app.database.FavoriteFoodEntity
import com.fitverse.app.database.FavoriteUserDao
import com.fitverse.app.database.RecentFoodEntity
import com.fitverse.app.model.FoodModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecentFoodViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<FoodModel>()

    private var userDao: FavoriteUserDao?
    private var userDb: FavoriteDatabase? = FavoriteDatabase.getDatabase(application)

    init {
        userDao = userDb?.favoriteUserDao()
    }

    fun addRecentFood(id: Int, name: String, photoUrl: String, description: String ) {
        CoroutineScope(Dispatchers.IO).launch {
            val food = RecentFoodEntity(
                id,
                name,
                photoUrl,
                description
            )
            userDao?.addRecentFood(food)
        }
    }

    suspend fun checkRecentFood(id: Int) = userDao?.checkRecentFood(id)

    fun deleteFromRecentFood(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.deleteRecentFood(id)
        }
    }
    fun getRecentFood(): LiveData<List<RecentFoodEntity>>? {
        return userDao?.getRecentFood()
    }
}