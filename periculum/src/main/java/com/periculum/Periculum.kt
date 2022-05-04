package com.periculum

import android.util.Log
import com.periculum.internal.PericulumManager
import com.periculum.models.PericulumCallback
import com.periculum.models.Response
import kotlinx.coroutines.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.periculum.internal.models.CreditScoreModel
import com.periculum.models.PericulumCallbackCreditScore


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
        token: String,
        periculumCallback: PericulumCallbackCreditScore
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = PericulumManager().startGenerateCreditScore(
                statementKey = statementKey,
                token = token
            )
            if (response.isError) {
                periculumCallback.onError(response.message, response.errorType)
                coroutineContext.cancel()
            } else {
                val gson: Gson = GsonBuilder().create()
                val creditScoreModel: CreditScoreModel = gson.fromJson(response.responseBody!!, CreditScoreModel::class.java)

                Log.d("Response", creditScoreModel.baseScore.toString())
                periculumCallback.onSuccess(creditScoreModel)
                coroutineContext.cancel()
            }
        }
    }

}