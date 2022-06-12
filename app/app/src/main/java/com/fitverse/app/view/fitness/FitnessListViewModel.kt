package com.fitverse.app.view.fitness

import android.util.Log
import androidx.constraintlayout.widget.Constraints
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

class FitnessListViewModel(private val pref: UserPreference) : ViewModel() {
    val listFitness = MutableLiveData<ArrayList<FitnessModel>>()

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun setFitness(token: String){
        ApiConfig.getApiService().getFitness("Bearer $token")
            .enqueue(object : Callback<ListFitnessResponse> {
                override fun onResponse(
                    call: Call<ListFitnessResponse>,
                    response: Response<ListFitnessResponse>
                ) {
                    if(response.code() == 200){
                        listFitness.postValue(response.body()?.data)
                    }
                }

                override fun onFailure(call: Call<ListFitnessResponse>, t: Throwable) {
                    Log.e(Constraints.TAG, "onFailure: ${t.message.toString()}")
                }
            })
    }

    fun setSearchFitness( token: String, query: String){
        ApiConfig.getApiService().searchFitness("Bearer $token",query)
            .enqueue(object : Callback<ListFitnessResponse>{
                override fun onResponse(
                    call: Call<ListFitnessResponse>,
                    response: Response<ListFitnessResponse>
                ) {
                    if (response.isSuccessful){
                        listFitness.postValue(response.body()?.data)
                    }
                }
                override fun onFailure(call: Call<ListFitnessResponse>, t: Throwable) {
                    t.message?.let { Log.d( "Failure", it) }
                }
            })
    }

    fun getFitness(): LiveData<ArrayList<FitnessModel>> {
        return listFitness
    }

}