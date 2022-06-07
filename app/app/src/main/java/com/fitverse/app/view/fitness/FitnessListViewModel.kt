package com.fitverse.app.view.fitness

import android.util.Log
import androidx.constraintlayout.widget.Constraints
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fitverse.app.api.ApiConfig
import com.fitverse.app.model.ListFitnessModel
import com.fitverse.app.model.UserPreference
import com.fitverse.app.response.ListFitnessResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FitnessListViewModel(private val pref: UserPreference) : ViewModel() {
    val listFitness = MutableLiveData<ArrayList<ListFitnessModel>>()

    fun setFitness(){
        ApiConfig.getApiService().getFitness()
            .enqueue(object : Callback<ListFitnessResponse> {
                override fun onResponse(
                    call: Call<ListFitnessResponse>,
                    response: Response<ListFitnessResponse>
                ) {
                    if(response.code() == 200){
                        listFitness.postValue(response.body()?.fitness)
                    }
                }

                override fun onFailure(call: Call<ListFitnessResponse>, t: Throwable) {
                    Log.e(Constraints.TAG, "onFailure: ${t.message.toString()}")
                }
            })
    }

    fun getFitness(): LiveData<ArrayList<ListFitnessModel>> {
        return listFitness
    }

}