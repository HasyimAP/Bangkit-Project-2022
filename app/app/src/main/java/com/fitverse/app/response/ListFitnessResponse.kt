package com.fitverse.app.response

import com.fitverse.app.model.ListFitnessModel
import com.fitverse.app.model.ListFoodModel
import com.google.gson.annotations.SerializedName

data class ListFitnessResponse (
    @SerializedName("fitness")
    val fitness: ArrayList<ListFitnessModel>
)