package com.nicelydone.movieapi.models.networks.connection

import com.nicelydone.movieapi.models.networks.response.DetailResponse
import com.nicelydone.movieapi.models.networks.response.SearchResponse
import com.nicelydone.movieapi.models.networks.response.TrendingTodayResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
   @GET("trending/movie/day")
   fun getTrendingToday(@Query("api_key") api_key: String,
                        @Query("page")page: Int): Call<TrendingTodayResponse>

   @GET("search/movie")
   fun getMoviesList(@Query("query") query: String?,
                     @Query("api_key") api_key: String): Call<SearchResponse>

   @GET("movie/{id}")
   fun getOneMovie(@Path("id") id: String,
                   @Query("api_key") api_key: String,
                   @Query("append_to_response") videos: String): Call<DetailResponse>
}