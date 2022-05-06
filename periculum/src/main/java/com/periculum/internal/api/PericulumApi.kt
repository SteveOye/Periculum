package com.periculum.internal.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.periculum.internal.models.AffordabilityModel
import com.periculum.internal.models.AnalyticsModel
import retrofit2.Call
import retrofit2.http.*


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

    @POST("/creditscore/{statementKey}")
    fun postGenerateCreditScore(
        @Header("Authorization") accessToken: String,
        @Path("statementKey") statementKey: String,
    ): Call<JsonObject>

    @GET("/creditscore/{statementKey}")
    fun getCreditScore(
        @Header("Authorization") accessToken: String,
        @Path("statementKey") statementKey: String,
    ): Call<JsonArray>

    @GET("/statements/{statementKey}/transactions")
    fun getStatementTransaction(
        @Header("Authorization") accessToken: String,
        @Path("statementKey") statementKey: String,
    ): Call<JsonArray>

}