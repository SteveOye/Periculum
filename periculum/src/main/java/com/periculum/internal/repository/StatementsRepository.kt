package com.periculum.internal.repository

import com.periculum.internal.api.RetrofitInstance
import com.periculum.internal.models.CreditScoreResponse
import com.periculum.internal.models.StatementsResponse
import com.periculum.internal.utils.Utils
import com.periculum.models.ErrorType

internal class StatementsRepository {

    internal suspend fun getStatement( accessToken: String, statementKey: String): StatementsResponse {
        return try {
            if (!Utils().isInternetConnected()) {
                StatementsResponse(
                    "There is no access to the internet. ",
                    isError = true,
                    errorType = ErrorType.InternetConnectionError
                )
            } else {
                val response = RetrofitInstance.api.getStatement(
                    accessToken = "Bearer $accessToken",
                    statementKey = "$statementKey",
                )
                val data = response.execute()
                if (data.isSuccessful) {
                    StatementsResponse(data.body()!!.toString(), isError = false)
                } else {
                    if(data.code() == 401) {
                        StatementsResponse(
                            responseBody = "Invalid Token. Unauthorized.",
                            true,
                            errorType = ErrorType.InvalidToken
                        )
                    }else {
                        StatementsResponse(
                            responseBody = data.message(),
                            true,
                            errorType = ErrorType.NetworkRequest
                        )
                    }
                }
            }
        } catch (e: Exception) {
            StatementsResponse(
                "${e.message}",
                isError = true,
                errorType = ErrorType.NetworkRequest
            )
        }
    }

}