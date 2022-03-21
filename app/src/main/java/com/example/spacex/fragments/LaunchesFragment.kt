package com.example.spacex.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.spacex.R
import com.example.spacex.databinding.FragmentLaunchesBinding

class LaunchesFragment : Fragment() {

    private lateinit var binding : FragmentLaunchesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLaunchesBinding.inflate(layoutInflater)

        Log.d("PUPA",requireArguments().getSerializable("array").toString())

        return binding.root
    }
}