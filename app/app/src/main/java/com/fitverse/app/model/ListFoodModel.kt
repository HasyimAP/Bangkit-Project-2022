package com.fitverse.app.model

import android.os.Parcelable
//import kotlinx.parcelize.Parcelize

//@Parcelize
data class ListFoodModel (
    val id_food: Int,
    val nama: String,
    val photoUrl: String,
    val description: String
)
