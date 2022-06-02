package com.fitverse.app.response

import com.fitverse.app.model.UserModel

data class LoginResponse (
    val error: String,
    val message: String,
    val loginResult: UserModel
)