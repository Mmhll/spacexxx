package com.example.spacex.retrofit

import com.example.spacex.dataclasses.Launches
import com.example.spacex.dataclasses.Rocket
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitApi {
    @GET("rockets")
    fun getRockets() : Call<ArrayList<Rocket>>
    @GET("launches")
    fun getLaunches() : Call<ArrayList<Launches>>
}