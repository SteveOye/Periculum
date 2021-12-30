package com.periculum.internal.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager

import com.periculum.internal.models.LocationModel
import com.periculum.internal.utils.PericulumDependency


internal class LocationRepository {

    @SuppressLint("MissingPermission")
    internal suspend fun getUserLocationData() : LocationModel {
        val locationManager = PericulumDependency.getApplicationContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        var accuracy: Float = 0F
        var altitude: Double = 0.0
        var bearing: Float = 0F
        var latitude: Double = 0.0
        var longitude: Double = 0.0
        var provider: String = ""
        var speed: Float = 0F
        var time: Long = 0L
        if (location == null) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                2000L,
                200F
            ) { location ->
                accuracy = location.accuracy
                altitude = location.altitude
                bearing = location.bearing
                latitude = location.latitude
                longitude = location.longitude
                provider = location.provider
                speed = location.speed
                time = location.time
            }
            return LocationModel(accuracy = accuracy, altitude = altitude, bearing = bearing, latitude = latitude, longitude = longitude, provider = provider, speed = speed, time = time)
        }else{
            accuracy = location.accuracy
            altitude = location.altitude
            bearing = location.bearing
            latitude = location.latitude
            longitude = location.longitude
            provider = location.provider
            speed = location.speed
            time = location.time
            return LocationModel(accuracy = accuracy, altitude = altitude, bearing = bearing, latitude = latitude, longitude = longitude, provider = provider, speed = speed, time = time)
        }
    }
}