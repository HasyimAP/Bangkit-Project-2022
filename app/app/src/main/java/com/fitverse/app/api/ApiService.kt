package com.fitverse.app.api

import com.fitverse.app.response.*
import retrofit2.Call
import retrofit2.http.*

//val pref = UserPreference
//val token = "Authorization : token ${pref.token}"

interface ApiService {
    @POST("register")
    fun register(
        @Query ("name") name : String,
        @Query ("email") email : String,
        @Query ("password") password : String,
        @Query ("gender") gender : String
    ): Call<GeneralResponse>

    @POST("login")
    fun login(
        @Query ("email") email : String,
        @Query ("password") password : String
    ): Call<LoginResponse>

    @GET("foods")
    fun getFood(
        @Header("Authorization") token: String,
    ): Call<ListFoodResponse>

    @GET("foods")
    fun searchFood(
        @Header("Authorization") token: String,
        @Query("name") name: String
    ): Call<ListFoodResponse>

    @GET("fitness")
    fun getFitness(
        @Header("Authorization") token: String,
    ): Call<ListFitnessResponse>

    @GET("fitness")
    fun searchFitness(
        @Header("Authorization") token: String,
        @Query("name") name: String
    ): Call<ListFitnessResponse>

    @GET("scanfood")
    fun findFoodDetail(
        @Header("Authorization") token: String,
        @Query("name") name: String
    ): Call<FoodDetailResponse>

    @GET("scan")
    fun findFitnessDetail(
        @Header("Authorization") token: String,
        @Query("name") name: String
    ): Call<FitnessDetailResponse>
}