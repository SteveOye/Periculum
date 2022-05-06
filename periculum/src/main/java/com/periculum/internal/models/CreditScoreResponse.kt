package com.periculum.internal.models

import com.periculum.models.ErrorType

internal data class CreditScoreResponse(
    val responseBody: String = "",
    val isError: Boolean,
    val errorType: ErrorType = ErrorType.Null)
