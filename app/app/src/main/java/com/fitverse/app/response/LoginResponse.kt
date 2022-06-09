package com.fitverse.app.response

import com.fitverse.app.model.UserModel

data class LoginResponse (
    val error: Boolean,
    val message: String,
    val data: UserModel
)