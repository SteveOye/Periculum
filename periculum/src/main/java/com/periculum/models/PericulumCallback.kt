package com.periculum.models

import com.periculum.internal.models.CreditScore
import com.periculum.internal.models.StatementTransaction
import com.periculum.internal.models.Statements


interface PericulumCallback {

    fun onSuccess(response: String )

    fun onError(message: String, errorType: ErrorType)
}

interface GenerateCreditScoreCallback {

    fun onSuccess(response: CreditScore )

    fun onError(message: String, errorType: ErrorType)
}

interface GetCreditScoreCallback {

    fun onSuccess(response: Array<CreditScore> )

    fun onError(message: String, errorType: ErrorType)
}

interface GetStatementTransactionCallback {

    fun onSuccess(response: Array<StatementTransaction> )

    fun onError(message: String, errorType: ErrorType)
}

interface GetStatementCallback {

    fun onSuccess(response: Statements)

    fun onError(message: String, errorType: ErrorType)
}