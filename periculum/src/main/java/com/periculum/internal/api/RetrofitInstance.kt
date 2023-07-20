package com.periculum.internal.api

import com.periculum.internal.utils.Utils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class RetrofitInstance {
    companion object {
        private val retrofit by lazy {

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