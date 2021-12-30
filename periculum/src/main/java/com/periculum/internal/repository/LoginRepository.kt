package com.periculum.internal.repository

import com.periculum.internal.models.LoginResponseModel
import okhttp3.Credentials

internal class LoginRepository {

    internal suspend fun logIn(
        merchantId: String,
        secretKey: String
    ): LoginResponseModel {
        val basicCredentials = Credentials.basic(merchantId, secretKey)
//            val periculumApi = PericulumApi.create().login(basicCredentials)

        /*periculumApi.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                onSuccess(response.body()!!.token)
                *//*TODO("Check if login was successful" +
                            "If true; Save the token in sharedPreferences, then send a successful callback to anyone that initiated the call" +
                            "if false; Send a failure callback")*//*
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    onError("Error Occurred", ErrorType.Error)
                }
            })*/
        return LoginResponseModel(status = 200, message = "success", token = "token")
    }
}