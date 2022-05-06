package com.periculum

import android.util.Log
import com.periculum.internal.PericulumManager
import kotlinx.coroutines.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.periculum.internal.models.Affordability
import com.periculum.internal.models.CreditScore
import com.periculum.internal.models.StatementTransaction
import com.periculum.internal.models.Statements
import com.periculum.models.*


object Periculum {

    fun analytics(
        phoneNumber: String,
        bvn: String,
        token: String,
        periculumCallback: PericulumCallback
    ) {
        runBlocking {

        }
        GlobalScope.launch(Dispatchers.Main) {
            val response: Response = PericulumManager().startAnalytics(
                phoneNumber = phoneNumber,
                bvn = bvn,
                token = token
            )
            if (response.isError) {
                periculumCallback.onError(response.message, response.errorType)
                coroutineContext.cancel()
            } else {
                periculumCallback.onSuccess(response.responseBody!!)
                coroutineContext.cancel()
            }
        }
    }

    fun affordability(
        dti: Double, loanTenure: Int, statementKey: Int,
        token: String,
        averageMonthlyLoanRepaymentAmount: Double? = null,
        averageMonthlyTotalExpenses: Double? = null,
        periculumCallback: PericulumCallback
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = PericulumManager().startAffordability(dti = dti, loanTenure = loanTenure, statementKey = statementKey, averageMonthlyTotalExpenses = averageMonthlyTotalExpenses, averageMonthlyLoanRepaymentAmount = averageMonthlyLoanRepaymentAmount, token = token)
            if (response.isError) {
                periculumCallback.onError(response.message, response.errorType)
                coroutineContext.cancel()
            } else {
                periculumCallback.onSuccess(response.responseBody!!)
                coroutineContext.cancel()
            }
        }
    }


    fun generateCreditScore(statementKey: String,
        accessToken: String,
        periculumCallback: GenerateCreditScoreCallback
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = PericulumManager().startGenerateCreditScore(
                statementKey = statementKey,
                accessToken = accessToken
            )
            if (response.isError) {
                periculumCallback.onError(response.message, response.errorType)
                coroutineContext.cancel()
            } else {
                val gson: Gson = GsonBuilder().create()
                val creditScore: CreditScore = gson.fromJson(response.responseBody!!, CreditScore::class.java)

                Log.d("Response", creditScore.baseScore.toString())
                periculumCallback.onSuccess(creditScore)
                coroutineContext.cancel()
            }
        }
    }

    fun getCreditScore(statementKey: String,
                            accessToken: String,
                            periculumCallback: GetCreditScoreCallback
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = PericulumManager().startGetCreditScore(
                statementKey = statementKey,
                accessToken = accessToken
            )
            if (response.isError) {
                periculumCallback.onError(response.message, response.errorType)
                coroutineContext.cancel()
            } else {
                val gson: Gson = GsonBuilder().create()
                val creditScore: Array<CreditScore> = gson.fromJson(response.responseBody!!, Array<CreditScore>::class.java)

                periculumCallback.onSuccess(creditScore)
                coroutineContext.cancel()
            }
        }
    }

    fun getStatementTransaction(statementKey: String,
                       accessToken: String,
                       periculumCallback: GetStatementTransactionCallback
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = PericulumManager().startGetStatementTransaction(
                statementKey = statementKey,
                accessToken = accessToken
            )
            if (response.isError) {
                periculumCallback.onError(response.message, response.errorType)
                coroutineContext.cancel()
            } else {
                val gson: Gson = GsonBuilder().create()
                val statements: Array<StatementTransaction> = gson.fromJson(response.responseBody!!, Array<StatementTransaction>::class.java)

                periculumCallback.onSuccess(statements)
                coroutineContext.cancel()
            }
        }
    }

    fun getStatement(statementKey: String,
                            accessToken: String,
                            periculumCallback: GetStatementCallback
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = PericulumManager().startGetStatement(
                statementKey = statementKey,
                accessToken = accessToken
            )
            if (response.isError) {
                periculumCallback.onError(response.message, response.errorType)
                coroutineContext.cancel()
            } else {
                val gson: Gson = GsonBuilder().create()
                val statements: Statements = gson.fromJson(response.responseBody!!, Statements::class.java)

                Log.d("Response", statements.processingStatus.toString())
                periculumCallback.onSuccess(statements)
                coroutineContext.cancel()
            }
        }
    }

    fun getAffordability(statementKey: String,
                                accessToken: String,
                                periculumCallback: GetAffordabilityCallback
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = PericulumManager().startGetAffordability(
                statementKey = statementKey,
                accessToken = accessToken
            )
            if (response.isError) {
                periculumCallback.onError(response.message, response.errorType)
                coroutineContext.cancel()
            } else {
                val gson: Gson = GsonBuilder().create()
                val affordability: Array<Affordability> = gson.fromJson(response.responseBody!!, Array<Affordability>::class.java)

                periculumCallback.onSuccess(affordability)
                coroutineContext.cancel()
            }
        }
    }



}