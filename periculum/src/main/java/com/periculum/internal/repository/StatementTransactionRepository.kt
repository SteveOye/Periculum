package com.periculum.internal.repository

import com.periculum.internal.api.RetrofitInstance
import com.periculum.internal.models.StatementTransactionResponse
import com.periculum.internal.utils.Utils
import com.periculum.models.ErrorType

internal class StatementTransactionRepository {
    internal suspend fun getStatementTransaction( accessToken: String, statementKey: String): StatementTransactionResponse {
        return try {
            if (!Utils().isInternetConnected()) {
                StatementTransactionResponse(
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
                    StatementTransactionResponse(data.body()!!.toString(), isError = false)
                } else {
                    if(data.code() == 401) {
                        StatementTransactionResponse(
                            responseBody = "Invalid Token. Unauthorized.",
                            true,
                            errorType = ErrorType.InvalidToken
                        )
                    }else {
                        StatementTransactionResponse(
                            responseBody = data.message(),
                            true,
                            errorType = ErrorType.NetworkRequest
                        )
                    }
                }
            }
        } catch (e: Exception) {
            StatementTransactionResponse(
                "${e.message}",
                isError = true,
                errorType = ErrorType.NetworkRequest
            )
        }
    }
}