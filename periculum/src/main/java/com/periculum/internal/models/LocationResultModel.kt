package com.periculum.internal.models

import com.periculum.models.ErrorType

internal data class LocationResultModel(
    val message: String,
    val errorType: ErrorType,
    val locationModel: LocationModel?
)
