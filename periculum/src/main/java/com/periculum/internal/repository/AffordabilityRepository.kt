package com.periculum.internal.repository

import android.util.Log
import com.google.gson.Gson
import com.periculum.internal.api.RetrofitInstance
import com.periculum.internal.models.AffordabilityModel
import com.periculum.internal.models.AffordabilityResponseModel
import com.periculum.internal.utils.Utils
import com.periculum.models.ErrorType
import com.periculum.models.Response

internal class AffordabilityRepository {

    internal suspend fun postAffordabilityDataToServer(affordabilityModel: AffordabilityModel): AffordabilityResponseModel {
        return try {
            if (!Utils().isInternetConnected()) {
                AffordabilityResponseModel(
                    "Internet Connection Required",
                    isError = true,
                    errorType = ErrorType.InternetConnectionError
                )
            } else {
                val response = RetrofitInstance.api.postAffordabilityData(
                    "Nucleusis",
                    affordabilityModel = affordabilityModel
                )
                val data = response.execute()
                if (data.isSuccessful) {
                    AffordabilityResponseModel(data.body()!!.toString(), isError = false)
                } else {
                    AffordabilityResponseModel(
                        responseBody = data.message(),
                        true,
                        errorType = ErrorType.NetworkRequest
                    )
                }
            }
        } catch (e: Exception) {
            if (!Utils().isInternetConnected()) {
                AffordabilityResponseModel(
                    "Internet Connection Required",
                    isError = true,
                    errorType = ErrorType.InternetConnectionError
                )
            }else if (!Utils().isSmsPermissionGranted()) {
                AffordabilityResponseModel(
                    "Sms Permission Required",
                    isError = true,
                    errorType = ErrorType.SmsPermissionError
                )
            }else if (!Utils().isLocationPermissionGranted()) {
                AffordabilityResponseModel(
                    "Access Location Permission Required",
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
                Log.e("TAG", "Error Called AFR")
                AffordabilityResponseModel(
                    "Error Occurred",
                    isError = true,
                    errorType = ErrorType.NetworkRequest
                )
            }
        }
    }
}