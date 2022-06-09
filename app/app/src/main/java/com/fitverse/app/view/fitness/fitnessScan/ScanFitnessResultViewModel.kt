package com.fitverse.app.view.fitness.fitnessScan

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fitverse.app.api.ApiConfig
import com.fitverse.app.model.FitnessModel
import com.fitverse.app.model.UserModel
import com.fitverse.app.model.UserPreference
import com.fitverse.app.response.ListFitnessResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScanFitnessResultViewModel (private val pref: UserPreference) : ViewModel() {
    val user = MutableLiveData<ArrayList<FitnessModel>>()

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun setFitnessDetail(token: String ,name: String) {
        ApiConfig.getApiService()
            .findFitnessDetail("Bearer $token",name)
            .enqueue(object : Callback<ListFitnessResponse> {
                override fun onResponse(
                    call: Call<ListFitnessResponse>,
                    response: Response<ListFitnessResponse>
                ) {
                    if (response.code() == 200) {
                        user.postValue(response.body()?.data)
                    }
                }

                override fun onFailure(call: Call<ListFitnessResponse>, t: Throwable) {
                    Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                }
            })
    }

    fun getFitnessDetail(): LiveData<ArrayList<FitnessModel>> {
        return user
    }

}