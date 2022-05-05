package com.periculum.internal.repository

import com.periculum.internal.api.RetrofitInstance
import com.periculum.internal.models.StatementResponse
import com.periculum.internal.utils.Utils
import com.periculum.models.ErrorType

internal class StatementRepository {
    internal suspend fun getStatementTransaction( accessToken: String, statementKey: String): StatementResponse {
        return try {
            if (!Utils().isInternetConnected()) {
                StatementResponse(
                    "There is no access to the internet. ",
                    isError = true,
                    errorType = ErrorType.InternetConnectionError
                )
            } else {
                val response = RetrofitInstance.api.getStatementTransaction(
                    accessToken = "Bearer $accessToken",
                    statementKey = statementKey,
                )
                val data = response.execute()
                if (data.isSuccessful) {
                    StatementResponse(data.body()!!.toString(), isError = false)
                } else {
                    if(data.code() == 401) {
                        StatementResponse(
                            responseBody = "Invalid Token. Unauthorized.",
                            true,
                            errorType = ErrorType.InvalidToken
                        )
                    }else {
                        StatementResponse(
                            responseBody = data.message(),
                            true,
                            errorType = ErrorType.NetworkRequest
                        )
                    }
                }
            }
        } catch (e: Exception) {
            StatementResponse(
                "${e.message}",
                isError = true,
                errorType = ErrorType.NetworkRequest
            )
        }
    }

}