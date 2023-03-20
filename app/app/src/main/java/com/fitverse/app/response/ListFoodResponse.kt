package com.fitverse.app.response

import com.fitverse.app.model.FoodModel
import com.google.gson.annotations.SerializedName

data class ListFoodResponse (
    @SerializedName("data")
    val data: ArrayList<FoodModel>
    )

