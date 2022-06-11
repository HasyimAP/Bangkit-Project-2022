package com.fitverse.app.view.food

import android.util.Log
import androidx.constraintlayout.widget.Constraints
import androidx.lifecycle.*
import com.fitverse.app.api.ApiConfig
import com.fitverse.app.model.FoodModel
import com.fitverse.app.model.UserModel
import com.fitverse.app.model.UserPreference
import com.fitverse.app.response.ListFoodResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodListViewModel(private val pref: UserPreference) : ViewModel() {
    val listFood = MutableLiveData<ArrayList<FoodModel>>()

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun setFood(token: String){
        ApiConfig.getApiService().getFood("Bearer $token")
            .enqueue(object : Callback<ListFoodResponse> {
                override fun onResponse(
                    call: Call<ListFoodResponse>,
                    response: Response<ListFoodResponse>
                ) {
                    if(response.code() == 200){
                        listFood.postValue(response.body()?.data)
                    }
                }

                override fun onFailure(call: Call<ListFoodResponse>, t: Throwable) {
                    Log.e(Constraints.TAG, "onFailure: ${t.message.toString()}")
                }
            })
    }

    fun setSearchFood( token: String, query: String){
        ApiConfig.getApiService().searchFood("Bearer $token",query)
            .enqueue(object : Callback<ListFoodResponse>{
                override fun onResponse(
                    call: Call<ListFoodResponse>,
                    response: Response<ListFoodResponse>
                ) {
                    if (response.isSuccessful){
                        listFood.postValue(response.body()?.data)
                    }
                }
                override fun onFailure(call: Call<ListFoodResponse>, t: Throwable) {
                    t.message?.let { Log.d( "Failure", it) }
                }
            })
    }

    fun getFood(): LiveData<ArrayList<FoodModel>> {
        return listFood
    }

}