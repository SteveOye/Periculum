package com.periculum.models


interface PericulumCallback {

    fun onSuccess(response: String)

    fun onError(message: String, errorType: ErrorType)
}