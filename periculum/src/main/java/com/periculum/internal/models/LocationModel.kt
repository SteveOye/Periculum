package com.periculum.internal.models

internal data class LocationModel(
    val accuracy: Float,
    val altitude: Double,
    val bearing: Float,
    val latitude: Double,
    val longitude: Double,
    val provider: String,
    val speed: Float,
    val time: Long
)
