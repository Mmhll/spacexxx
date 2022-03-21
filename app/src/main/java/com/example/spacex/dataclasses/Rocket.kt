package com.example.spacex.dataclasses

data class Rocket(
    val height: height,
    val diameter: diameter,
    val mass : mass,
    val first_stage: first_stage,
    val second_stage: second_stage,
    val payload_weights : ArrayList<payload_weights>,
    val flickr_images : ArrayList<String>,
    val name : String,
    val cost_per_launch : Double,
    val first_flight : String,
    val country : String,
    val id : String
)

data class height(
    val meters : Double,
    val feet : Double
)
data class diameter(
    val meters : Double,
    val feet : Double
)
data class mass(
    val kg : Double,
    val lb : Double
)

data class payload_weights(
    val kg : Int,
    val lb : Int
)

data class first_stage(
    val engines : Int,
    val fuel_amount_tons : Double,
    val burn_time_sec : Int
)
data class second_stage(
    val engines : Int,
    val fuel_amount_tons : Double,
    val burn_time_sec : Int
)

