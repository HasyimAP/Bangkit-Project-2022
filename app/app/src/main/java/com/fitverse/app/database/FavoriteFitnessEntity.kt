package com.fitverse.app.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favorite_fitness")
@Parcelize
data class FavoriteFitnessEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "photoUrl")
    val photoUrl: String,

    @ColumnInfo(name = "description")
    val description: String,
) : Parcelable