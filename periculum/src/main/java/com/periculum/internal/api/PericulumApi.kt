package com.periculum.internal.api

import com.periculum.internal.models.AnalyticsModel
import com.periculum.internal.models.AnalyticsResponseModel
import com.periculum.internal.models.LoginResponseModel
import com.periculum.internal.utils.Utils
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


internal interface PericulumApi {

    @POST("/login")
    fun login(@Header("Authorization") credentials: String): Call<LoginResponseModel>

    @POST("/mobile/analytics")
    fun analytics(@Header("Authorization") token: String, @Header("x-tenant") xTenant: String, @Body analyticsBody: AnalyticsModel): Call<AnalyticsResponseModel>

    companion object {
        internal fun create(): PericulumApi {
            val client = OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS).build()
            val retrofit = Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Utils.baseUrl)
                .build()
            return retrofit.create(PericulumApi::class.java)
        }
    }
}