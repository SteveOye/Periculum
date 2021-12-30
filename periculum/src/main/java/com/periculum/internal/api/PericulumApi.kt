package com.periculum.internal.api

import com.google.gson.JsonObject
import com.periculum.internal.models.AnalyticsModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.*


internal interface PericulumApi {

    @POST("/login")
    fun login(@Header("Authorization") credentials: String): Call<JsonObject>

    @POST("/mobile/analytics")
    fun postAnalytics(
        @Header("Authorization") token: String,
        @Header("x-tenant") xTenant: String,
        @Body analyticsBody: AnalyticsModel
    ): Call<JsonObject>
}