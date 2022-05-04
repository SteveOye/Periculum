package com.periculum.models

import com.google.gson.JsonObject

data class Response(
    val message: String = "",
    val responseBody: String? = null,
    val isError: Boolean,
    val errorType: ErrorType = ErrorType.Null)
