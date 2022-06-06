package com.fitverse.app.api

import com.fitverse.app.model.LoginModel
import com.fitverse.app.model.RegisterModel
import com.fitverse.app.response.GeneralResponse
import com.fitverse.app.response.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("register")
    fun register(
        @Body regis : RegisterModel
    ): Call<GeneralResponse>

    @POST("login")
    fun login(
        @Query ("email") email : String,
        @Query ("pass") pass : String
    ): Call<LoginResponse>

}