package com.periculum.internal.api

import com.google.gson.JsonObject
import com.periculum.internal.models.AffordabilityModel
import com.periculum.internal.models.AnalyticsModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


internal interface PericulumApi {

    @POST("/mobile/analytics")
    fun postAnalytics(
        @Header("Authorization") token: String,
        @Body analyticsBody: AnalyticsModel
    ): Call<JsonObject>

    @POST("/affordability")
    fun postAffordabilityData(
        @Header("Authorization") token: String,
        @Body affordabilityModel: AffordabilityModel
    ): Call<JsonObject>
}