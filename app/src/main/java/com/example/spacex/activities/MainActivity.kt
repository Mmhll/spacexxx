package com.example.spacex.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.spacex.R
import com.example.spacex.dataclasses.Rocket
import com.example.spacex.fragments.MainFragment
import com.example.spacex.retrofit.MyRetrofit
import com.example.spacex.retrofit.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment())
            .commit()
    }
}