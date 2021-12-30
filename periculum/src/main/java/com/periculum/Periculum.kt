package com.periculum

import com.periculum.internal.PericulumManager
import com.periculum.models.Response
import com.periculum.models.VendorData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object Periculum {

    suspend fun start(
        vendorData: VendorData
    ): Response = withContext(Dispatchers.IO) {
        return@withContext PericulumManager().startProcess(vendorData)
    }
}