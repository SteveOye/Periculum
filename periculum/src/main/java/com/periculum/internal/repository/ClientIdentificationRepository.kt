package com.periculum.internal.repository

import com.periculum.internal.api.RetrofitInstance
import com.periculum.internal.models.ClientData
import com.periculum.internal.models.ClientIdentificationResponse
import com.periculum.internal.models.StatementsResponse
import com.periculum.internal.utils.Utils
import com.periculum.models.ErrorType

internal class ClientIdentificationRepository {

    internal suspend fun patchClientIdentification( accessToken: String, clientData: ClientData): ClientIdentificationResponse {
        return try {
            if (!Utils().isInternetConnected()) {
                ClientIdentificationResponse(
                    "There is no access to the internet. ",
                    isError = true,
                    errorType = ErrorType.InternetConnectionError
                )
            } else {
                val response = RetrofitInstance.api.patchClientIdentification(
                    accessToken = "Bearer $accessToken",
                    clientData = clientData,
                )
                val data = response.execute()
                if (data.isSuccessful) {
                    ClientIdentificationResponse(data.code().toString(), isError = false)
                } else {
                    if(data.code() == 401) {
                        ClientIdentificationResponse(
                            responseBody = "Invalid Token. Unauthorized.",
                            true,
                            errorType = ErrorType.InvalidToken
                        )
                    }else {
                        ClientIdentificationResponse(
                            responseBody = data.message(),
                            true,
                            errorType = ErrorType.NetworkRequest
                        )
                    }
                }
            }
        } catch (e: Exception) {
            ClientIdentificationResponse(
                "${e.message}",
                isError = true,
                errorType = ErrorType.NetworkRequest
            )
        }
    }

}