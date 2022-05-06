package com.periculum.internal.repository

import com.periculum.internal.api.RetrofitInstance
import com.periculum.internal.models.CreditScoreResponse
import com.periculum.internal.utils.Utils
import com.periculum.models.ErrorType

internal class CreditScoreRepository {

    internal suspend fun postGenerateCreditScore( accessToken: String, statementKey: String): CreditScoreResponse{
        return try {
            if (!Utils().isInternetConnected()) {
                CreditScoreResponse(
                    "There is no access to the internet. ",
                    isError = true,
                    errorType = ErrorType.InternetConnectionError
                )
            } else {
                val response = RetrofitInstance.api.postGenerateCreditScore(
                    accessToken = "Bearer $accessToken",
                    statementKey = "$statementKey",
                )
                val data = response.execute()
                if (data.isSuccessful) {
                    CreditScoreResponse(data.body()!!.toString(), isError = false)
                } else {
                    if(data.code() == 401) {
                        CreditScoreResponse(
                            responseBody = "Invalid Token. Unauthorized.",
                            true,
                            errorType = ErrorType.InvalidToken
                        )
                    }else {
                        CreditScoreResponse(
                            responseBody = data.message(),
                            true,
                            errorType = ErrorType.NetworkRequest
                        )
                    }
                }
            }
        } catch (e: Exception) {
            CreditScoreResponse(
               "${e.message}",
                isError = true,
                errorType = ErrorType.NetworkRequest
            )
        }
    }

    internal suspend fun getCreditScore( accessToken: String, statementKey: String): CreditScoreResponse{
        return try {
            if (!Utils().isInternetConnected()) {
                CreditScoreResponse(
                    "There is no access to the internet. ",
                    isError = true,
                    errorType = ErrorType.InternetConnectionError
                )
            } else {
                val response = RetrofitInstance.api.getCreditScore(
                    accessToken = "Bearer $accessToken",
                    statementKey = statementKey,
                )
                val data = response.execute()
                if (data.isSuccessful) {
                    CreditScoreResponse(data.body()!!.toString(), isError = false)
                } else {
                    if(data.code() == 401) {
                        CreditScoreResponse(
                            responseBody = "Invalid Token. Unauthorized.",
                            true,
                            errorType = ErrorType.InvalidToken
                        )
                    }else {
                        CreditScoreResponse(
                            responseBody = data.message(),
                            true,
                            errorType = ErrorType.NetworkRequest
                        )
                    }
                }
            }
        } catch (e: Exception) {
            CreditScoreResponse(
                "${e.message}",
                isError = true,
                errorType = ErrorType.NetworkRequest
            )
        }
    }

}