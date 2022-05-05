package com.periculum.models

import com.periculum.internal.models.CreditScoreModel


interface PericulumCallback {

    fun onSuccess(response: String )

    fun onError(message: String, errorType: ErrorType)
}

interface CallbackGenerateCreditScore {

    fun onSuccess(response: CreditScoreModel )

    fun onError(message: String, errorType: ErrorType)
}

interface CallbackGetCreditScore {

    fun onSuccess(response: Array<CreditScoreModel> )

    fun onError(message: String, errorType: ErrorType)
}