package com.fitverse.app.view.food.foodScan

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.fitverse.app.api.ApiConfig
import com.fitverse.app.model.FoodModel
import com.fitverse.app.model.UserModel
import com.fitverse.app.model.UserPreference
import com.fitverse.app.response.FoodDetailResponse
import com.fitverse.app.response.ListFoodResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScanFoodResultViewModel (private val pref: UserPreference) : ViewModel() {
    val food = MutableLiveData<FoodModel>()

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun setFoodDetail(token: String ,name: String) {
        ApiConfig.getApiService()
            .findFoodDetail("Bearer $token",name)
            .enqueue(object : Callback<FoodDetailResponse> {
                override fun onResponse(
                    call: Call<FoodDetailResponse>,
                    response: Response<FoodDetailResponse>
                ) {
                    if (response.code() == 200) {
                        food.postValue(response.body()?.data)
                    }
                }

                override fun onFailure(call: Call<FoodDetailResponse>, t: Throwable) {
                    Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                }

            })
    }

    fun getFoodDetail(): LiveData<FoodModel> {
        return food
    }

}