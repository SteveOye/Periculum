package com.periculum

import com.periculum.internal.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object Periculum {

    fun logIn(merchantId: String, secretKey: String) {
        GlobalScope.launch(Dispatchers.Main) {
            LoginRepository().logIn(
                merchantId,
                secretKey,
                onSuccess = {

                },
                onFailure = {

                }
            )
        }
    }

}