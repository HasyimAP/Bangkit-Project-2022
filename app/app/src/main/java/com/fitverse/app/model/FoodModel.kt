package com.fitverse.app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodModel (
    val id: Int,
    val name: String,
    val photoUrl: String,
    val description: String
): Parcelable
