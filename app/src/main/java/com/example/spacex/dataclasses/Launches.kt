package com.example.spacex.dataclasses

data class Launches(
    val rocket : String,
    val success : Boolean,
    val name : String,
    val date_utc : String
)
