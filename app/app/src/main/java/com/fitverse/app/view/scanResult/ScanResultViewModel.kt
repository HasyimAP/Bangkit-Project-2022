package com.fitverse.app.view.scanResult

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fitverse.app.api.ApiConfig
import com.fitverse.app.response.FoodDetailResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScanResultViewModel (application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<FoodDetailResponse>()

    fun setFoodDetail(id: String) {
        ApiConfig.getApiService()
            .findFoodDetail(id)
            ?.enqueue(object : Callback<FoodDetailResponse?> {
                override fun onResponse(
                    call: Call<FoodDetailResponse?>,
                    response: Response<FoodDetailResponse?>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<FoodDetailResponse?>, t: Throwable) {
                    Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                }

            })

    }

    fun getFoodDetail(): LiveData<FoodDetailResponse> {
        return user
    }

}