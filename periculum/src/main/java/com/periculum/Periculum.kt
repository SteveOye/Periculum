package com.periculum

import android.util.Log
import com.periculum.internal.PericulumManager
import com.periculum.models.PericulumCallback
import com.periculum.models.Response
import com.periculum.models.VendorData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object Periculum {

    fun start(vendorData: VendorData, periculumCallback: PericulumCallback) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = PericulumManager().startProcess(vendorData)
            if(response.isError) {
                periculumCallback.onError(response.message, response.errorType)
            }else {
                periculumCallback.onSuccess(response)
            }
        }
    }
}