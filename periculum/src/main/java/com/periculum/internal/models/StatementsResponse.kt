package com.periculum.internal.models

import com.periculum.models.ErrorType

data class StatementsResponse (
    val responseBody: String = "",
    val isError: Boolean,
    val errorType: ErrorType = ErrorType.Null
)