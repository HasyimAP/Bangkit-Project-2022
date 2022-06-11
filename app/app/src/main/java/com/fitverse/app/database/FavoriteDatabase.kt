package com.fitverse.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [
    FavoriteFitnessEntity::class,
    FavoriteFoodEntity::class,
    RecentFitnessEntity::class,
    RecentFoodEntity::class],
    version = 1
)
abstract class FavoriteDatabase: RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao
    companion object {
        @Volatile
        private var INSTANCE: FavoriteDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): FavoriteDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteDatabase::class.java, "fitverse_database")
                        .build()
                }
            }
            return INSTANCE as FavoriteDatabase
        }
    }
}