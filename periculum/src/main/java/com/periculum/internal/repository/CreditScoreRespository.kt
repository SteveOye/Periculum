package com.periculum.internal.repository

import com.periculum.internal.api.RetrofitInstance
import com.periculum.internal.models.AffordabilityResponseModel
import com.periculum.internal.models.CreditScoreResponseModel
import com.periculum.internal.utils.Utils
import java.util.*
import com.periculum.models.ErrorType

internal class CreditScoreRespository {

    internal suspend fun postGenerateCreditScore( accessToken: String, statementKey: String): CreditScoreResponseModel{
        return try {
            if (!Utils().isInternetConnected()) {
                CreditScoreResponseModel(
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
                    CreditScoreResponseModel(data.body()!!.toString(), isError = false)
                } else {
                    if(data.code() == 401) {
                        CreditScoreResponseModel(
                            responseBody = "Invalid Token. Unauthorized.",
                            true,
                            errorType = ErrorType.InvalidToken
                        )
                    }else {
                        CreditScoreResponseModel(
                            responseBody = data.message(),
                            true,
                            errorType = ErrorType.NetworkRequest
                        )
                    }
                }
            }
        } catch (e: Exception) {
            CreditScoreResponseModel(
               "${e.message}",
                isError = true,
                errorType = ErrorType.NetworkRequest
            )
        }
    }

    internal suspend fun getCreditScore( accessToken: String, statementKey: String): CreditScoreResponseModel{
        return try {
            if (!Utils().isInternetConnected()) {
                CreditScoreResponseModel(
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
                    CreditScoreResponseModel(data.body()!!.toString(), isError = false)
                } else {
                    if(data.code() == 401) {
                        CreditScoreResponseModel(
                            responseBody = "Invalid Token. Unauthorized.",
                            true,
                            errorType = ErrorType.InvalidToken
                        )
                    }else {
                        CreditScoreResponseModel(
                            responseBody = data.message(),
                            true,
                            errorType = ErrorType.NetworkRequest
                        )
                    }
                }
            }
        } catch (e: Exception) {
            CreditScoreResponseModel(
                "${e.message}",
                isError = true,
                errorType = ErrorType.NetworkRequest
            )
        }
    }

}