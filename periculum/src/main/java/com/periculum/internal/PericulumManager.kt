package com.periculum.internal

import com.periculum.internal.models.AffordabilityModel
import com.periculum.internal.repository.AffordabilityRepository
import com.periculum.internal.repository.AnalyticsRepository
import com.periculum.internal.repository.CreditScoreRespository
import com.periculum.internal.utils.Utils
import com.periculum.models.Response
import com.periculum.models.ErrorType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


internal class PericulumManager {

    suspend fun startAnalytics(phoneNumber: String, bvn: String, token: String): Response =
        withContext(Dispatchers.IO) {
            try {
                if (token.isEmpty()) {
                    Response(
                        message = "Invalid access token",
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
                        .postAnalyticsDataToServer(phoneNumber, bvn, token = token)
                    if (analyticsResponse.isError) {
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

    suspend fun startAffordability(dti: Double, loanTenure: Int, statementKey: Int, averageMonthlyTotalExpenses: Double?, averageMonthlyLoanRepaymentAmount: Double?, token: String): Response =
        withContext(Dispatchers.IO) {
            try {
                if(dti < 0.0 || dti > 1) {
                    Response(
                        message = "Invalid DTI. DTI value must be between 0 - 1",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.InvalidData
                    )
                }else if (token.isEmpty()) {
                    Response(
                        message = "Invalid access token",
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
                } else {
                    val affordabilityResponse =
                        AffordabilityRepository().postAffordabilityDataToServer(
                            AffordabilityModel(
                                dti = dti,
                                loanTenure = loanTenure,
                                statementKey = statementKey,
                                averageMonthlyLoanRepaymentAmount = averageMonthlyLoanRepaymentAmount,
                                averageMonthlyTotalExpenses = averageMonthlyTotalExpenses
                            ),
                            token = token
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
            } catch (e: Exception) {
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

    suspend fun startGenerateCreditScore(statementKey: String, token: String): Response =
        withContext(Dispatchers.IO) {
            try {
                 if (token.isEmpty()) {
                    Response(
                        message = "Invalid access token",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.InvalidToken
                    )
                } else if (statementKey.isEmpty()) {
                     Response(
                         message = "Invalid Statement key",
                         responseBody = null,
                         isError = true,
                         errorType = ErrorType.InvalidToken
                     )
                 }  else if (!Utils().isInternetConnected()) {
                    Response(
                        message = "There is no access to the internet.",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.InternetConnectionError
                    )
                } else {
                    val creditScoreResponse =
                        CreditScoreRespository().postGenerateCreditScore(
                            token = token,
                            statementKey = statementKey,
                        )
                    if (!creditScoreResponse.isError) {
                        Response(
                            message = "Success",
                            isError = false,
                            responseBody = creditScoreResponse.responseBody
                        )
                    } else {
                        Response(
                            message = creditScoreResponse.responseBody,
                            isError = true,
                            errorType = creditScoreResponse.errorType,
                            responseBody = null
                        )
                    }
                }
            } catch (e: Exception) {
                if (!Utils().isInternetConnected()) {
                    Response(
                        message = "There is no access to the internet.",
                        isError = true,
                        errorType = ErrorType.InternetConnectionError,
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

    suspend fun stateCreditScore(statementKey: String, token: String): Response =
        withContext(Dispatchers.IO) {
            try {
                 if (token.isEmpty()) {
                    Response(
                        message = "Invalid access token",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.InvalidToken
                    )
                } else if (statementKey.isEmpty()) {
                     Response(
                         message = "Invalid Statement key",
                         responseBody = null,
                         isError = true,
                         errorType = ErrorType.InvalidToken
                     )
                 }  else if (!Utils().isInternetConnected()) {
                    Response(
                        message = "There is no access to the internet.",
                        responseBody = null,
                        isError = true,
                        errorType = ErrorType.InternetConnectionError
                    )
                } else {
                    val creditScoreResponse =
                        CreditScoreRespository().getCreditScore(
                            token = token,
                            statementKey = statementKey,
                        )
                    if (!creditScoreResponse.isError) {
                        Response(
                            message = "Success",
                            isError = false,
                            responseBody = creditScoreResponse.responseBody
                        )
                    } else {
                        Response(
                            message = creditScoreResponse.responseBody,
                            isError = true,
                            errorType = creditScoreResponse.errorType,
                            responseBody = null
                        )
                    }
                }
            } catch (e: Exception) {
                if (!Utils().isInternetConnected()) {
                    Response(
                        message = "There is no access to the internet.",
                        isError = true,
                        errorType = ErrorType.InternetConnectionError,
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