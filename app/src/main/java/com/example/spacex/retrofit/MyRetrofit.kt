package com.example.spacex.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyRetrofit {
    fun getRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.spacexdata.com/v4/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}