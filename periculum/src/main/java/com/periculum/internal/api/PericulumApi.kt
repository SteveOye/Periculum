package com.periculum.internal.api

import com.google.gson.JsonObject
import com.periculum.internal.models.AffordabilityModel
import com.periculum.internal.models.AnalyticsModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


internal interface PericulumApi {

    @POST("/login")
    fun login(@Header("Authorization") credentials: String): Call<JsonObject>

    @POST("/mobile/analytics")
    fun postAnalytics(
        @Header("Authorization") token: String,
        @Header("x-tenant") xTenant: String,
        @Body analyticsBody: AnalyticsModel
    ): Call<JsonObject>

    @POST("/affordability")
    fun postAffordabilityData(
        @Header("x-tenant") xTenant: String,
        @Body affordabilityModel: AffordabilityModel
    ): Call<JsonObject>
}