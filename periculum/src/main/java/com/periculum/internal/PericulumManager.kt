package com.periculum.internal

import android.util.Log
import com.periculum.internal.repository.AnalyticsRepository
import com.periculum.internal.utils.Utils
import com.periculum.models.Response
import com.periculum.models.ErrorType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


internal class PericulumManager {

    suspend fun startAnalyticsV1(publicKey: String, phoneNumber: String, bvn: String) =
        withContext(Dispatchers.IO) {
            try {
                if (publicKey.isEmpty()) {
                    Response(
                        message = "Invalid publickKey",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.InvalidToken
                    )
                } else if (!Utils().isInternetConnected()) {
                    Response(
                        message = "There is no access to the internet.",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.InternetConnectionError
                    )
                } else if (!Utils().isSmsPermissionGranted()) {
                    Response(
                        message = "Permission to read SMS messages from the device has been denied.",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.SmsPermissionError
                    )
                } else if (!Utils().isLocationPermissionGranted()) {
                    Response(
                        message = "Permission to read the location of the device has been denied.",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.LocationPermissionError
                    )
                } else if (!Utils().isLocationEnabled()) {
                    Response(
                        message = "Location not enabled.",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.LocationNotEnabledError
                    )
                } else {
                    val analyticsResponse = AnalyticsRepository()
                        .mobileAnaylticsV1(publicKey, phoneNumber, bvn)
                    if (analyticsResponse.isError) {
                        Log.d("TAG", "startAnalytics: ${analyticsResponse.message}")
                        Response(
                            message = analyticsResponse.message,
                            isError = true,
                            errorType = analyticsResponse.errorType,
                            responseBody = null
                        )
                    } else {
                        Response(
                            message = "Success",
                            isError = false,
                            responseBody = analyticsResponse.response
                        )
                    }
                }
            } catch (e: Exception) {
                Log.d("TAG", "startAnalytics:  ${e.message}")
                if (!Utils().isInternetConnected()) {
                    Response(
                        message = "There is no access to the internet.",
                        isError = true,
                        errorType = ErrorType.InternetConnectionError,
                        responseBody = null
                    )
                } else if (!Utils().isLocationEnabled()) {
                    Response(
                        message = "Location not enabled.",
                        isError = true,
                        errorType = ErrorType.LocationNotEnabledError,
                        responseBody = null
                    )
                } else {
                    Response(
                        message = "Error Occurred",
                        isError = true,
                        errorType = ErrorType.UnknownError,
                        responseBody = null
                    )
                }
            }
        }

    suspend fun startAnalyticsV2(publicKey: String, phoneNumber: String, bvn: String) =
        withContext(Dispatchers.IO) {
            try {
                if (publicKey.isEmpty()) {
                    Response(
                        message = "Invalid publickKey",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.InvalidToken
                    )
                } else if (!Utils().isInternetConnected()) {
                    Response(
                        message = "There is no access to the internet.",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.InternetConnectionError
                    )
                } else if (!Utils().isSmsPermissionGranted()) {
                    Response(
                        message = "Permission to read SMS messages from the device has been denied.",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.SmsPermissionError
                    )
                } else if (!Utils().isLocationPermissionGranted()) {
                    Response(
                        message = "Permission to read the location of the device has been denied.",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.LocationPermissionError
                    )
                } else if (!Utils().isLocationEnabled()) {
                    Response(
                        message = "Location not enabled.",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.LocationNotEnabledError
                    )
                } else {
                    val analyticsResponse = AnalyticsRepository()
                        .mobileInsightV2(publicKey, phoneNumber, bvn)
                    if (analyticsResponse.isError) {
                        Log.d("TAG", "startAnalytics: ${analyticsResponse.message}")
                        Response(
                            message = analyticsResponse.message,
                            isError = true,
                            errorType = analyticsResponse.errorType,
                            responseBody = null
                        )
                    } else {
                        Response(
                            message = "Success",
                            isError = false,
                            responseBody = analyticsResponse.response
                        )
                    }
                }
            } catch (e: Exception) {
                Log.d("TAG", "startAnalytics:  ${e.message}")
                if (!Utils().isInternetConnected()) {
                    Response(
                        message = "There is no access to the internet.",
                        isError = true,
                        errorType = ErrorType.InternetConnectionError,
                        responseBody = null
                    )
                } else if (!Utils().isLocationEnabled()) {
                    Response(
                        message = "Location not enabled.",
                        isError = true,
                        errorType = ErrorType.LocationNotEnabledError,
                        responseBody = null
                    )
                } else {
                    Response(
                        message = "Error Occurred",
                        isError = true,
                        errorType = ErrorType.UnknownError,
                        responseBody = null
                    )
                }
            }
        }

    suspend fun startPatchAnalyticsV2(publicKey: String, phoneNumber: String?, bvn: String?, overviewKey: String) =
        withContext(Dispatchers.IO) {
            try {
                if (publicKey.isEmpty()) {
                    Response(
                        message = "Invalid publickKey",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.InvalidToken
                    )
                } else if (!Utils().isInternetConnected()) {
                    Response(
                        message = "There is no access to the internet.",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.InternetConnectionError
                    )
                } else if (!Utils().isSmsPermissionGranted()) {
                    Response(
                        message = "Permission to read SMS messages from the device has been denied.",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.SmsPermissionError
                    )
                } else if (!Utils().isLocationPermissionGranted()) {
                    Response(
                        message = "Permission to read the location of the device has been denied.",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.LocationPermissionError
                    )
                } else if (!Utils().isLocationEnabled()) {
                    Response(
                        message = "Location not enabled.",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.LocationNotEnabledError
                    )
                } else {
                    val analyticsResponse = AnalyticsRepository()
                        .updateMobileInsightV2(publicKey, overviewKey, phoneNumber!!, bvn!!)
                    if (analyticsResponse.isError) {
                        Log.d("TAG", "startAnalytics: ${analyticsResponse.message}")
                        Response(
                            message = analyticsResponse.message,
                            isError = true,
                            errorType = analyticsResponse.errorType,
                            responseBody = null
                        )
                    } else {
                        Response(
                            message = "Success",
                            isError = false,
                            responseBody = analyticsResponse.response
                        )
                    }
                }
            } catch (e: Exception) {
                Log.d("TAG", "startAnalytics:  ${e.message}")
                if (!Utils().isInternetConnected()) {
                    Response(
                        message = "There is no access to the internet.",
                        isError = true,
                        errorType = ErrorType.InternetConnectionError,
                        responseBody = null
                    )
                } else if (!Utils().isLocationEnabled()) {
                    Response(
                        message = "Location not enabled.",
                        isError = true,
                        errorType = ErrorType.LocationNotEnabledError,
                        responseBody = null
                    )
                } else {
                    Response(
                        message = "Error Occurred",
                        isError = true,
                        errorType = ErrorType.UnknownError,
                        responseBody = null
                    )
                }
            }
        }
}