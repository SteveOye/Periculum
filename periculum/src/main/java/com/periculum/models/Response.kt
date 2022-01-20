package com.periculum.models

data class Response (val message: String = "", val responseBody: String?, val isError: Boolean, val errorType: ErrorType = ErrorType.Null)