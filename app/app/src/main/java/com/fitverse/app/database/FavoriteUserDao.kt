package com.fitverse.app.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDao {
    //fitness query
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteFitness(favoriteFitnessEntity: FavoriteFitnessEntity)

    @Query("SELECT * from favorite_fitness ")
    fun getFavoriteFitness(): LiveData<List<FavoriteFitnessEntity>>

    @Query("SELECT count(*) FROM favorite_fitness WHERE favorite_fitness.id = :id")
    suspend fun checkFavoriteFitness(id: Int): Int

    @Query("DELETE FROM favorite_fitness WHERE favorite_fitness.id= :id")
    suspend fun deleteFavoriteFitness(id: Int): Int

    //food query
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteFood(favoriteFoodEntity: FavoriteFoodEntity)

    @Query("SELECT * from favorite_food")
    fun getFavoriteFood(): LiveData<List<FavoriteFoodEntity>>

    @Query("SELECT count(*) FROM favorite_food WHERE favorite_food.id = :id")
    suspend fun checkFavoriteFood(id: Int): Int

    @Query("DELETE FROM favorite_food WHERE favorite_food.id= :id")
    suspend fun deleteFavoriteFood(id: Int): Int

}