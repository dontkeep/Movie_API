package com.nicelydone.movieapi.models.networks.connection

import com.nicelydone.movieapi.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiConfig {
   companion object {
      val base_url = BuildConfig.BASE_URL
      fun getAPIService(): ApiService{
         val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

         val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

         val retrofit = Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
         return retrofit.create(ApiService::class.java)
      }
   }
}