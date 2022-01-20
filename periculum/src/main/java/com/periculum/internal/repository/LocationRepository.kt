package com.periculum.internal.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.periculum.internal.models.LocationModel
import com.periculum.internal.models.LocationResultModel
import com.periculum.internal.utils.PericulumDependency
import com.periculum.models.ErrorType
import kotlinx.coroutines.delay
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

const val TAG = "Location"
internal class LocationRepository {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    internal suspend fun getUserLocationData(): LocationResultModel {
        try {
            return suspendCoroutine { continuation ->
                val locationManager = PericulumDependency.getApplicationContext()
                    .getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (location == null) {
                    try {
                        fusedLocationClient =
                            LocationServices.getFusedLocationProviderClient(PericulumDependency.getApplicationContext())
                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { location ->
                                continuation.resume(
                                    LocationResultModel(
                                        message = "",
                                        errorType = ErrorType.Null,
                                        locationModel = LocationModel(
                                            accuracy = location.accuracy,
                                            altitude = location.altitude,
                                            bearing = location.bearing,
                                            latitude = location.latitude,
                                            longitude = location.longitude,
                                            provider = location.provider,
                                            speed = location.speed,
                                            time = location.time
                                        )
                                    )
                                )
                            }
                            .addOnFailureListener {
                                continuation.resume(
                                    LocationResultModel(
                                        message = "Error while getting location. Location not fetched",
                                        errorType = ErrorType.UnknownError,
                                        locationModel = null
                                    )
                                )
                                Log.e(TAG, "Error While Retrieving Location Data", it)
                            }
                    } catch (e: Exception) {
                        continuation.resume(LocationResultModel(
                            message = "Error while getting location. Location not fetched",
                            errorType = ErrorType.UnknownError,
                            locationModel = null
                        ))
                    }
                } else {
                    continuation.resume(LocationResultModel(
                        message = "",
                        errorType = ErrorType.Null,
                        locationModel = LocationModel(
                            accuracy = location.accuracy,
                            altitude = location.altitude,
                            bearing = location.bearing,
                            latitude = location.latitude,
                            longitude = location.longitude,
                            provider = location.provider,
                            speed = location.speed,
                            time = location.time
                        )
                    ))
                }
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