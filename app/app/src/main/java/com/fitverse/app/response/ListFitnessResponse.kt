package com.fitverse.app.response

import com.fitverse.app.model.FitnessModel
import com.google.gson.annotations.SerializedName

data class ListFitnessResponse (
    @SerializedName("data")
    val data: ArrayList<FitnessModel>
)