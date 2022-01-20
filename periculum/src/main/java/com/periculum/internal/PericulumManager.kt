package com.periculum.internal

import android.telephony.PhoneNumberUtils
import android.util.Log
import androidx.core.text.isDigitsOnly
import com.periculum.internal.models.AffordabilityModel
import com.periculum.internal.repository.AffordabilityRepository
import com.periculum.internal.repository.AnalyticsRepository
import com.periculum.internal.utils.Utils
import com.periculum.models.ErrorType
import com.periculum.models.Response
import com.periculum.models.VendorData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


internal class PericulumManager {

    suspend fun startProcess(vendorData: VendorData): Response =
        withContext(Dispatchers.IO) {
            try {
                if (vendorData.bvn.length != 10 || !vendorData.bvn.isDigitsOnly()) {
                    Response(
                        message = "Invalid BVN Number",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.InvalidVendorData
                    )
                } else if (vendorData.phoneNumber.isEmpty() || !PhoneNumberUtils.isGlobalPhoneNumber(
                        vendorData.phoneNumber
                    )
                ) {
                    Response(
                        message = "Invalid Phone Number",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.InvalidVendorData
                    )
                } else if (!Utils().isInternetConnected()) {
                    Response(
                        message = "Internet Connection Required",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.InternetConnectionError
                    )
                } else if (!Utils().isSmsPermissionGranted()) {
                    Response(
                        message = "Sms Permission Required",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.SmsPermissionError
                    )
                }else if (!Utils().isLocationPermissionGranted()) {
                    Response(
                        message = "Access Location Permission Required",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.LocationPermissionError
                    )
                }else if (!Utils().isLocationEnabled()) {
                    Response(
                        message = "Location not enabled.",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.LocationNotEnabledError
                    )
                }else {
                    val analyticsResponse = AnalyticsRepository().postAnalyticsDataToServer(
                        vendorData.phoneNumber,
                        vendorData.bvn,
                        token = vendorData.token
                    )
                    if (analyticsResponse.isError) {
                        Response(
                            message = analyticsResponse.responseBody,
                            isError = true,
                            errorType = analyticsResponse.errorType,
                            responseBody = null
                        )
                    } else {
                        val affordabilityResponse =
                            AffordabilityRepository().postAffordabilityDataToServer(
                                AffordabilityModel(
                                    dti = vendorData.dti,
                                    loanTenure = vendorData.loanTenure,
                                    analyticsResponse.responseBody.toInt()
                                ),
                                token = vendorData.token
                            )
                        if (!affordabilityResponse.isError) {
                            Response(
                                message = "Success",
                                isError = false,
                                responseBody = affordabilityResponse.responseBody
                            )
                        } else {
                            Response(
                                message = affordabilityResponse.responseBody,
                                isError = true,
                                errorType = affordabilityResponse.errorType,
                                responseBody = null
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                if (!Utils().isInternetConnected()) {
                    Response(
                        message = "Internet Connection Required",
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