package com.periculum.internal.repository

import com.periculum.internal.api.PericulumApi
import com.periculum.internal.models.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class LoginRepository {

    internal suspend fun logIn(
        merchantId: String,
        secretKey: String,
        onSuccess: () -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            val basicCredentials = Credentials.basic(merchantId, secretKey)
            val periculumApi = PericulumApi.create().login(basicCredentials)

            periculumApi.enqueue(object : Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    TODO("Check if login was successful" +
                            "If true; Save the token in sharedPreferences, then send a successful callback to anyone that initiated the call" +
                            "if false; Send a failure callback")
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}