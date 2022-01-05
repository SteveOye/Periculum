package com.periculum.internal.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.periculum.internal.models.LocationModel
import com.periculum.internal.models.LocationResultModel
import com.periculum.internal.utils.PericulumDependency
import com.periculum.models.ErrorType
import kotlinx.coroutines.delay


internal class LocationRepository {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    internal suspend fun getUserLocationData(): LocationResultModel {
        try {
            val locationManager = PericulumDependency.getApplicationContext()
                .getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            var accuracy: Float = 0F
            var altitude: Double = 0.0
            var bearing: Float = 0F
            var latitude: Double = -0.0
            var longitude: Double = 0.0
            var provider: String = ""
            var speed: Float = 0F
            var time: Long = 0L
            if (location == null) {
                fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(PericulumDependency.getApplicationContext())
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location ->
                        accuracy = location.accuracy
                        altitude = location.altitude
                        bearing = location.bearing
                        latitude = location.latitude
                        longitude = location.longitude
                        provider = location.provider
                        speed = location.speed
                        time = location.time
                    }
                for (count in 0..4) {
                    if (latitude == -0.0) {
                        delay(2000)
                    } else {
                        break
                    }
                }
                if (latitude == -0.0) {
                    return LocationResultModel(
                        message = "Error while getting location. Location not fetched",
                        errorType = ErrorType.UnknownError,
                        locationModel = null
                    )
                } else {
                    return LocationResultModel(
                        message = "",
                        errorType = ErrorType.Null,
                        locationModel = LocationModel(
                            accuracy = accuracy,
                            altitude = altitude,
                            bearing = bearing,
                            latitude = latitude,
                            longitude = longitude,
                            provider = provider,
                            speed = speed,
                            time = time
                        )
                    )
                }
            } else {
                accuracy = location.accuracy
                altitude = location.altitude
                bearing = location.bearing
                latitude = location.latitude
                longitude = location.longitude
                provider = location.provider
                speed = location.speed
                time = location.time
                return LocationResultModel(
                    message = "",
                    errorType = ErrorType.Null,
                    locationModel = LocationModel(
                        accuracy = accuracy,
                        altitude = altitude,
                        bearing = bearing,
                        latitude = latitude,
                        longitude = longitude,
                        provider = provider,
                        speed = speed,
                        time = time
                    )
                )
            }
        } catch (e: Exception) {
            return LocationResultModel(
                message = "Error while getting location. Location not fetched",
                errorType = ErrorType.UnknownError,
                locationModel = null
            )
        }
    }
}