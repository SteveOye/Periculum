package com.periculum.models

import com.periculum.internal.models.*


interface PericulumCallback {

    fun onSuccess(response: String )

    fun onError(message: String, errorType: ErrorType)
}

interface MobileInsightCallback {

    fun onSuccess(response: OverviewKey )

    fun onError(message: String, errorType: ErrorType)
}

