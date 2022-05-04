package com.periculum.models

import com.periculum.internal.models.CreditScoreModel


interface PericulumCallback {

    fun onSuccess(response: String )

    fun onError(message: String, errorType: ErrorType)
}

interface PericulumCallbackCreditScore {

    fun onSuccess(response: CreditScoreModel )

    fun onError(message: String, errorType: ErrorType)
}