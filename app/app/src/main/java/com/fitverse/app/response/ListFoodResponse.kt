package com.fitverse.app.response

import com.fitverse.app.model.ListFoodModel
import com.google.gson.annotations.SerializedName

data class ListFoodResponse (
    @SerializedName("food")
    val food: ArrayList<ListFoodModel>
    )

