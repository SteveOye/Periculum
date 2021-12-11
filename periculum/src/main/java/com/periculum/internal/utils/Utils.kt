package com.periculum.internal.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class Utils {

    internal fun checkIfSmsPermissionIsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            PericulumDependency.getApplicationContext(),
            Manifest.permission.READ_SMS
        ) == PackageManager.PERMISSION_GRANTED
    }

    internal suspend fun getTokenFromCache() : String =
        withContext(Dispatchers.IO) {
            return@withContext PericulumDependency.getSharedPreferences().getString(tokenKey, "")!!
        }

    internal suspend fun saveTokenToCache(token: String) {
        withContext(Dispatchers.IO) {
            with(PericulumDependency.getSharedPreferences().edit()) {
                putString(tokenKey, token)
                apply()
            }
        }
    }

    internal suspend fun getLastSixMonthSmsFromDevice() {
        withContext(Dispatchers.IO) {

        }
    }


    companion object{
        internal const val sharedPreferencesKey = "Periculum"
        internal const val tokenKey = "Token"
        internal const val baseUrl = "http://www.periculum.com"
    }
}