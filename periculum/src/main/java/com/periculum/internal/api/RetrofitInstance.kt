package com.periculum.internal.api

import com.periculum.internal.utils.Utils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
//            val clientBuilder = OkHttpClient.Builder()
//            val loggingInterceptor  = HttpLoggingInterceptor()
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//            clientBuilder.addInterceptor(loggingInterceptor)

            val client = OkHttpClient.Builder()
                .connectTimeout(200, TimeUnit.SECONDS)
                .readTimeout(200, TimeUnit.SECONDS).build()

//                .client(clientBuilder.build())
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Utils.baseUrl)
                .build()
        }

        internal val api by lazy {
            retrofit.create(PericulumApi::class.java)
        }
    }
}