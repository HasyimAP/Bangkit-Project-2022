package com.fitverse.app.response

import com.fitverse.app.model.FoodModel
import com.google.gson.annotations.SerializedName

data class FoodDetailResponse (
    @SerializedName("data")
    val data: FoodModel
)
