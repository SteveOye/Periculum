package com.periculum.internal.api

import android.net.Credentials
import com.periculum.internal.models.LoginResponse
import com.periculum.internal.utils.Utils
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Header
import retrofit2.http.POST

internal interface PericulumApi {

    @POST("/login")
    fun login(@Header("Authorization") credentials: String): Call<LoginResponse>

    companion object {
        internal fun create(): PericulumApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Utils.baseUrl)
                .build()
            return retrofit.create(PericulumApi::class.java)
        }
    }
}