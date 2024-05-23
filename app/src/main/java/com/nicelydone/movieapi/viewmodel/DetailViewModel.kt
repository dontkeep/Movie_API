package com.nicelydone.movieapi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicelydone.movieapi.models.networks.connection.ApiConfig
import com.nicelydone.movieapi.models.networks.response.DetailResponse
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class DetailViewModel: ViewModel() {
   private val _detailResult = MutableLiveData<DetailResponse>()
   private val _loading = MutableLiveData<Boolean>()

   val detailResult: LiveData<DetailResponse> = _detailResult
   val loading: LiveData<Boolean> = _loading

   init {
      _loading.value = false
   }

   fun getDetail(id: String, api_key: String) {
      _loading.value = true
      val client = ApiConfig.getAPIService().getOneMovie(id, api_key, videos = "videos")
      client.enqueue(object: retrofit2.Callback<DetailResponse> {
         override fun onResponse(p0: Call<DetailResponse>, p1: Response<DetailResponse>) {
            if(p1.isSuccessful){
               _loading.value = false
               val res = p1.body()
               _detailResult.value = res
            }else{
               Log.e(MainViewModel.TAG, "Error : ${p1.message()}")
            }
         }

         override fun onFailure(p0: Call<DetailResponse>, p1: Throwable) {
            _loading.value = false
            Log.e(MainViewModel.TAG, "Error: ${p1.message}")
         }

      })
   }

   companion object {
      val TAG = "DetailViewModel"
   }
}