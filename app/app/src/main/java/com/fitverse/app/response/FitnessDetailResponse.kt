package com.fitverse.app.response

import com.fitverse.app.model.FitnessModel
import com.google.gson.annotations.SerializedName

data class FitnessDetailResponse (
    @SerializedName("data")
    val data: FitnessModel
)