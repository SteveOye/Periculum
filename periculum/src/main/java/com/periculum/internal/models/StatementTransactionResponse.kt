package com.periculum.internal.models

import com.periculum.models.ErrorType

data class StatementTransactionResponse (
    val responseBody: String = "",
    val isError: Boolean,
    val errorType: ErrorType = ErrorType.Null
)