package com.periculum.models

interface PericulumCallback {

    fun onSuccess(response: Response)

    fun onError(message: String, errorType: ErrorType)
}