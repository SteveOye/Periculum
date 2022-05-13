package com.periculum.internal.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.periculum.internal.models.Affordability
import com.periculum.internal.models.AnalyticsModel
import com.periculum.internal.models.ClientData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


internal interface PericulumApi {

    @POST("/mobile/analytics")
    fun postAnalytics(
        @Header("Authorization") accessToken: String,
        @Body analyticsBody: AnalyticsModel
    ): Call<JsonArray>

    @POST("/affordability")
    fun postAffordabilityData(
        @Header("Authorization") accessToken: String,
        @Body affordability: Affordability
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

    @GET("/statements/{statementKey}")
    fun getStatement(
        @Header("Authorization") accessToken: String,
        @Path("statementKey") statementKey: String,
    ): Call<JsonObject>

    @GET("/affordability/{statementKey}")
    fun getAffordability(
        @Header("Authorization") accessToken: String,
        @Path("statementKey") statementKey: String,
    ): Call<JsonArray>

    @PATCH("/statements/identification")
    fun patchClientIdentification(
        @Header("Authorization") accessToken: String,
        @Body clientData: ClientData
    ): Call<ResponseBody>
}