package com.nicelydone.movieapi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicelydone.movieapi.BuildConfig
import com.nicelydone.movieapi.models.networks.connection.ApiConfig
import com.nicelydone.movieapi.models.networks.response.SearchResponse
import com.nicelydone.movieapi.models.networks.response.TrendingTodayResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
   private val _trendingResult = MutableLiveData<TrendingTodayResponse>()
   private val _loading = MutableLiveData<Boolean>()
   private val _searchResult = MutableLiveData<SearchResponse>()

   val searchResult: LiveData<SearchResponse> = _searchResult
   val trendingResult: LiveData<TrendingTodayResponse> = _trendingResult
   val loading: LiveData<Boolean> = _loading

   init {
      _loading.value = false
   }

   fun getSearchResult(api_key: String, query: String?){
      _loading.value = true
      val client = ApiConfig.getAPIService().getMoviesList(query, api_key)
      client.enqueue(object: Callback<SearchResponse>{
         override fun onResponse(p0: Call<SearchResponse>, p1: Response<SearchResponse>) {
            _loading.value = false
            if (p1.isSuccessful){
               val res = p1.body()
               _searchResult.value = res
            } else {
               Log.e(TAG, "Error : ${p1.message()}")
            }
         }

         override fun onFailure(p0: Call<SearchResponse>, p1: Throwable) {
            _loading.value = false
            Log.e(TAG, "Error: ${p1.message}")
         }
      })
   }

   fun getTrendingResult(api_key: String){
      _loading.value = true
      val client = ApiConfig.getAPIService().getTrendingToday(api_key, 1)
      client.enqueue(object: Callback<TrendingTodayResponse>{
         override fun onResponse(
            p0: Call<TrendingTodayResponse>,
            p1: Response<TrendingTodayResponse>
         ) {
            _loading.value = false
            if (p1.isSuccessful){
               val res = p1.body()
               _trendingResult.value = res
            } else {
               Log.e(TAG, "Error : ${p1.message()}")
            }
         }

         override fun onFailure(p0: Call<TrendingTodayResponse>, p1: Throwable) {
            _loading.value = false
            Log.e(TAG, "Error: ${p1.message}")
         }
      })
   }
   companion object{
      val TAG = "MainViewModel"
   }
}