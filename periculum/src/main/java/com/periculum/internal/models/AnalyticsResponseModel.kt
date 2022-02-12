package com.periculum.internal.models

import com.google.gson.JsonObject
import com.periculum.models.ErrorType
internal data class AnalyticsResponseModel(val response: String? = null, val message: String = "", val isError: Boolean, val errorType: ErrorType = ErrorType.Null)