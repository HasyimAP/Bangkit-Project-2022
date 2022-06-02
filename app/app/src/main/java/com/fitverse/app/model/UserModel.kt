package com.fitverse.app.model

data class UserModel(
    val id_user: String,
    val email: String,
    val pass: String,
    val nama_user: String,
    val jenis_kelamin: String,
    val isLogin: Boolean)