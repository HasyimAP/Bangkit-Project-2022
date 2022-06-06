package com.fitverse.app.api

import com.fitverse.app.model.LoginModel
import com.fitverse.app.model.RegisterModel
import com.fitverse.app.response.GeneralResponse
import com.fitverse.app.response.ListFoodResponse
import com.fitverse.app.response.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("registrasi")
    fun register(
        @Query ("nama_user") nama_user : String,
        @Query ("email") email : String,
        @Query ("pass") pass : String,
        @Query ("jenis_kelamin") jenis_kelamin : String

    ): Call<GeneralResponse>

    @POST("login")
    fun login(
        @Query ("email") email : String,
        @Query ("pass") pass : String
    ): Call<LoginResponse>

    @GET("food")
    fun getFood(): Call<ListFoodResponse>
}