package com.fitverse.app

import android.util.Log
import androidx.constraintlayout.widget.Constraints
import androidx.lifecycle.*
import com.fitverse.app.api.ApiConfig
import com.fitverse.app.model.ListFoodModel
import com.fitverse.app.model.UserModel
import com.fitverse.app.model.UserPreference
import com.fitverse.app.response.ListFoodResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListViewModel(private val pref: UserPreference) : ViewModel() {
    val list_food = MutableLiveData<List<ListFoodModel>>()

    fun setFood(){
        ApiConfig.getApiService().getFood()
            .enqueue(object : Callback<ListFoodResponse> {
            override fun onResponse(
                call: Call<ListFoodResponse>,
                response: Response<ListFoodResponse>
            ) {
                if(response.isSuccessful){
                    list_food.postValue(response.body()?.food)
                }
            }

            override fun onFailure(call: Call<ListFoodResponse>, t: Throwable) {
                Log.e(Constraints.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFood() : MutableLiveData<List<ListFoodModel>> = list_food

}