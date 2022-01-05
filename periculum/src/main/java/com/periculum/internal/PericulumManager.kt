package com.periculum.internal

import android.util.Log
import com.periculum.internal.models.AffordabilityModel
import com.periculum.internal.repository.AffordabilityRepository
import com.periculum.internal.repository.AnalyticsRepository
import com.periculum.internal.repository.LoginRepository
import com.periculum.internal.utils.Utils
import com.periculum.models.ErrorType
import com.periculum.models.Response
import com.periculum.models.VendorData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


internal class PericulumManager {

    suspend fun startProcess(vendorData: VendorData): Response =
        withContext(Dispatchers.IO) {
            if (!Utils().isInternetConnected()) {
                Response(
                    message = "Internet Connection Required",
                    isError = true,
                    errorType = ErrorType.InternetConnectionError
                )
            }else if (!Utils().isSmsPermissionGranted()) {
                Response(
                    message = "Sms Permission Required",
                    isError = true,
                    errorType = ErrorType.SmsPermissionError
                )
            }else if (!Utils().isLocationPermissionGranted()) {
                Response(
                    message = "Access Location Permission Required",
                    isError = true,
                    errorType = ErrorType.LocationPermissionError
                )
            }else if (!Utils().isLocationEnabled()) {
                Response(
                    message = "Location not enabled.",
                    isError = true,
                    errorType = ErrorType.LocationNotEnabledError
                )
            }else {
                try {
                    val loginResponse =
                        LoginRepository().logIn(
                            vendorData.merchantId,
                            secretKey = vendorData.secretKey
                        )
                    val analyticsResponse = AnalyticsRepository().postAnalyticsDataToServer(
                        vendorData.phoneNumber,
                        vendorData.bvn,
                        token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik1VSkJOVUk0UkRFek9FVTBORGd4UWpVMVJqTTJPVEJEUXpRMFF6bEJRa1F6UWpnd1JETkVSQSJ9.eyJodHRwczovL2luc2lnaHRzLXBlcmljdWx1bS5jb20vdGVuYW50IjoicGVyaWxlbmRhIiwiaXNzIjoiaHR0cHM6Ly9wZXJpY3VsdW0tdGVjaG5vbG9naWVzLWluYy5hdXRoMC5jb20vIiwic3ViIjoiYXV0aDB8NjFiNzhjMjhmZWUyZmUwMDY5OGM5MjEwIiwiYXVkIjoiaHR0cHM6Ly9sb2NhbGhvc3Q6NDQzMTYiLCJpYXQiOjE2Mzk0ODM4MTAsImV4cCI6MTYzOTU3MDIxMCwiYXpwIjoidEpHamxZUUpvTDhmdGRiUVM5aVJxNlo0Y2FNSzBCYUEiLCJzY29wZSI6ImVtYWlsIiwiZ3R5IjoicGFzc3dvcmQifQ.pRIbybAchWhNHNWqqro6AXOQEzJ1n4GtKIm2Ykmroo6EDAinwv47-7M8qOznRwWND5sEtSCORGYn2HTWeAZ53-pJ31VYinO22Zgunfvt8DeUSpNvL61MpiCmuQa0Hpp0VWlnjX5or-EiEgG5ddmGeOCt6hmxgqD3FGKgzeXFsQYTZfxm6n7SRPlUvENM--jGcKRwRQM7MnRmq9MZYFoEYt_KlUnJ5DFS8wVLPOA2ClUn-Gd8BONuS6ysod8iIdkiQlSoG2U-OpYfBC882TQlv4WjP51_9NG-CYJGKM1c-z2DM86mhAs9XYCmVMJc-yBvHPLG7h2Tjx5k64CbcuUBdg"
                    )
                    if (analyticsResponse.isError) {
                        Response(
                            message = analyticsResponse.responseBody,
                            isError = true,
                            errorType = analyticsResponse.errorType
                        )
                    } else {
                        val affordabilityResponse =
                            AffordabilityRepository().postAffordabilityDataToServer(
                                AffordabilityModel(
                                    dti = vendorData.dti,
                                    loanTenure = vendorData.loanTenure,
                                    analyticsResponse.responseBody.toInt()
                                )
                            )
                        if (!affordabilityResponse.isError) {
                            Response(message = affordabilityResponse.responseBody, isError = false)
                        } else {
                            Response(
                                message = affordabilityResponse.responseBody,
                                isError = true,
                                errorType = affordabilityResponse.errorType
                            )
                        }
                    }
                } catch (e: Exception) {
                    if (!Utils().isInternetConnected()) {
                        Response(
                            message = "Internet Connection Required",
                            isError = true,
                            errorType = ErrorType.InternetConnectionError
                        )
                    } else if (!Utils().isLocationEnabled()) {
                        Response(
                            message = "Location not enabled.",
                            isError = true,
                            errorType = ErrorType.LocationNotEnabledError
                        )
                    } else {
                        Log.e("TAG", "Error Called PM")
                        Response(
                            message = "Error Occurred",
                            isError = true,
                            errorType = ErrorType.UnknownError
                        )
                    }
                }
            }
        }
}