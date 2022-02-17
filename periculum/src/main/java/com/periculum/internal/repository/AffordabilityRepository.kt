package com.periculum.internal.repository

import android.util.Log
import com.periculum.internal.api.RetrofitInstance
import com.periculum.internal.models.AffordabilityModel
import com.periculum.internal.models.AffordabilityResponseModel
import com.periculum.internal.models.AnalyticsResponseModel
import com.periculum.internal.utils.Utils
import com.periculum.models.ErrorType

internal class AffordabilityRepository {

    internal suspend fun postAffordabilityDataToServer(affordabilityModel: AffordabilityModel, token: String): AffordabilityResponseModel {
        return try {
            if (!Utils().isInternetConnected()) {
                AffordabilityResponseModel(
                    "There is no access to the internet. ",
                    isError = true,
                    errorType = ErrorType.InternetConnectionError
                )
            } else {
                val response = RetrofitInstance.api.postAffordabilityData(
                    token = "Bearer $token",
                    affordabilityModel = affordabilityModel
                )
                val data = response.execute()
                if (data.isSuccessful) {
                    AffordabilityResponseModel(data.body()!!.toString(), isError = false)
                } else {
                    if(data.code() == 401) {
                        AffordabilityResponseModel(
                            responseBody = "Invalid Token. Unauthorized.",
                            true,
                            errorType = ErrorType.InvalidToken
                        )
                    }else {
                        AffordabilityResponseModel(
                            responseBody = data.message(),
                            true,
                            errorType = ErrorType.NetworkRequest
                        )
                    }
                }
            }
        } catch (e: Exception) {
            if (!Utils().isInternetConnected()) {
                AffordabilityResponseModel(
                    "There is no access to the internet.",
                    isError = true,
                    errorType = ErrorType.InternetConnectionError
                )
            }else if (!Utils().isSmsPermissionGranted()) {
                AffordabilityResponseModel(
                    "Permission to read SMS messages from the device has been denied.",
                    isError = true,
                    errorType = ErrorType.SmsPermissionError
                )
            }else if (!Utils().isLocationPermissionGranted()) {
                AffordabilityResponseModel(
                    "Permission to read the location of the device has been denied.",
                    isError = true,
                    errorType = ErrorType.LocationPermissionError
                )
            }else if (!Utils().isLocationEnabled()) {
                AffordabilityResponseModel(
                    "Location not enabled.",
                    isError = true,
                    errorType = ErrorType.LocationNotEnabledError
                )
            }else {
                AffordabilityResponseModel(
                    "An error occurred while submitting the request",
                    isError = true,
                    errorType = ErrorType.NetworkRequest
                )
            }
        }
    }
}