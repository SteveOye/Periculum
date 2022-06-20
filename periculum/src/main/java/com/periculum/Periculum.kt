package com.periculum

import android.util.Log
import com.periculum.internal.PericulumManager
import kotlinx.coroutines.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.periculum.internal.models.*
import com.periculum.models.*
import java.security.PublicKey


object Periculum {

    fun analyticsV1(
        phoneNumber: String,
        bvn: String,
        publicKey: String,
        periculumCallback: PericulumCallback
    ) {
        runBlocking {
        }
        GlobalScope.launch(Dispatchers.Main) {
            val response: Response = PericulumManager().startAnalyticsV1(
                phoneNumber = phoneNumber,
                bvn = bvn,
                publicKey = publicKey
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

    fun analyticsV2(
        phoneNumber: String,
        bvn: String,
        publicKey: String,
        periculumCallback: MobileInsightCallback
    ) {
        runBlocking {
        }
        GlobalScope.launch(Dispatchers.Main) {
            val response: Response = PericulumManager().startAnalyticsV2(
                phoneNumber = phoneNumber,
                bvn = bvn,
                publicKey = publicKey
            )
            if (response.isError) {
                periculumCallback.onError(response.message, response.errorType)
                coroutineContext.cancel()
            } else {
                val gson: Gson = GsonBuilder().create()
                val overviewKeyResponse: OverviewKey = gson.fromJson(response.responseBody.toString(), OverviewKey::class.java)
                periculumCallback.onSuccess(overviewKeyResponse)
                coroutineContext.cancel()
            }
        }
    }

    fun updateAnalyticsV2(
        phoneNumber: String?,
        bvn: String?,
        publicKey: String,
        overviewKey: String,
        periculumCallback: MobileInsightCallback
    ) {
        runBlocking {
        }
        GlobalScope.launch(Dispatchers.Main) {
            val response: Response = PericulumManager().startPatchAnalyticsV2(
                phoneNumber = phoneNumber!!,
                bvn = bvn!!,
                publicKey = publicKey,
                overviewKey =  overviewKey,
            )
            if (response.isError) {
                periculumCallback.onError(response.message, response.errorType)
                coroutineContext.cancel()
            } else {
                Log.d("TAG", "updateAnalyticsV2: " + response.responseBody.toString())
                val gson: Gson = GsonBuilder().create()
                val overviewKeyResponse: OverviewKey = gson.fromJson(response.responseBody.toString(), OverviewKey::class.java)
                periculumCallback.onSuccess(overviewKeyResponse)
                coroutineContext.cancel()
            }
        }
    }
}