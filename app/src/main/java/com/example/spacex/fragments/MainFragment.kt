package com.example.spacex.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.spacex.databinding.FragmentMainBinding
import com.example.spacex.dataclasses.Rocket
import com.example.spacex.dataclasses.RocketValues
import com.example.spacex.recyclers.ViewPagerRecycler
import com.example.spacex.retrofit.MyRetrofit
import com.example.spacex.retrofit.RetrofitApi
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {
   private lateinit var binding : FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)

        val prefs = requireActivity().getSharedPreferences("Values", Context.MODE_PRIVATE)
        var values = RocketValues(
            prefs.getInt("Height", 0),
            prefs.getInt("Diameter", 0),
            prefs.getInt("Mass", 0),
            prefs.getInt("Weight", 0)
        )

        var retrofit = MyRetrofit().getRetrofit().create(RetrofitApi::class.java)
            .getRockets().enqueue(object : Callback<ArrayList<Rocket>>{
                override fun onResponse(
                    call: Call<ArrayList<Rocket>>,
                    response: Response<ArrayList<Rocket>>
                ) {
                    if (response.isSuccessful){
                        response.body()!!.let {
                            binding.viewPagerRocket.adapter = ViewPagerRecycler(requireContext(), it, values, requireActivity())
                            TabLayoutMediator(binding.tabs, binding.viewPagerRocket){_,_ ->}.attach()
                        }

                    }
                }
                override fun onFailure(call: Call<ArrayList<Rocket>>, t: Throwable) {

                }
            })
        return binding.root
    }
}