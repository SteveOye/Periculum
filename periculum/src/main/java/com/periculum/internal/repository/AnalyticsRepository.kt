package com.periculum.internal.repository

import android.content.Context
import android.content.Context.BATTERY_SERVICE
import android.content.pm.PackageManager
import android.os.BatteryManager
import android.os.Build
import android.telephony.TelephonyManager
import android.util.Log
import com.periculum.internal.api.RetrofitInstance
import com.periculum.internal.models.*
import com.periculum.internal.utils.PericulumDependency
import com.periculum.internal.utils.Utils
import com.periculum.internal.utils.getProjectName
import com.periculum.models.ErrorType
import java.security.PublicKey
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random


internal class AnalyticsRepository {


    internal suspend fun mobileAnaylticsV1(
        publicKey: String,
        phoneNumber: String,
        bvn: String,
    ): AnalyticsResponseModel {
//        val locationResult = LocationRepository().getUserLocationData()
//        if (locationResult.errorType != ErrorType.Null) {
//            return AnalyticsResponseModel(message = locationResult.message, isError =  true, errorType = locationResult.errorType)
//        }
        return try {
            val response = RetrofitInstance.api.postAnalytics(analyticsBody = getAnalyticsData(phoneNumber, bvn, publicKey))
            val data = response.execute()
            if(data.isSuccessful) {
                AnalyticsResponseModel(response = data.body()!!.toString(), isError = false)
            }else {
                if(data.code() == 401) {
                    AnalyticsResponseModel(message = "Invalid Public key", isError = true, errorType = ErrorType.InvalidToken)
                }else {
                    AnalyticsResponseModel(message = data.message(), isError = true, errorType = ErrorType.NetworkRequest)
                }
            }
        } catch (e: Exception) {
            if (!Utils().isInternetConnected()) {
                AnalyticsResponseModel(message = "There is no access to the internet.", isError = true, errorType = ErrorType.InternetConnectionError)
            }else if (!Utils().isSmsPermissionGranted()) {
                AnalyticsResponseModel(
                    message = "Permission to read SMS messages from the device has been denied.",
                    isError = true,
                    errorType = ErrorType.SmsPermissionError
                )
            }
//            else if (!Utils().isLocationPermissionGranted()) {
//                AnalyticsResponseModel(
//                    message = "Permission to read the location of the device has been denied.",
//                    isError = true,
//                    errorType = ErrorType.LocationPermissionError
//                )
//            } else if (!Utils().isLocationEnabled()) {
//                AnalyticsResponseModel(
//                    message = "Location not enabled.",
//                    isError = true,
//                    errorType = ErrorType.LocationNotEnabledError
//                )
//            }
            else {
                AnalyticsResponseModel(
                    message = "An error occurred while submitting the request",
                    isError = true,
                    errorType = ErrorType.NetworkRequest
                )
            }
        }
    }

    internal suspend fun mobileInsightV2(
        publicKey: String,
        phoneNumber: String,
        bvn: String,
    ): AnalyticsResponseModel {

        return try {
            val response = RetrofitInstance.api.mobileInsightsV2(analyticsBody = getAnalyticsData(phoneNumber, bvn, publicKey))
            val data = response.execute()
            if(data.isSuccessful) {
                AnalyticsResponseModel(response = data.body()!!.toString(), isError = false)
            }else {
                when {
                    data.code() == 401 -> {
                        AnalyticsResponseModel(message = "Invalid Public key", isError = true, errorType = ErrorType.InvalidToken)
                    }
                    data.code() == 400 -> {
                        AnalyticsResponseModel(message = "There is already an Insights with the unique id provided. Please use the PATCH endpoint to update the existing Insights.", isError = true, errorType = ErrorType.InvalidToken)
                    }
                    else -> {
                        AnalyticsResponseModel(message = data.message(), isError = true, errorType = ErrorType.NetworkRequest)
                    }
                }
            }
        } catch (e: Exception) {
            if (!Utils().isInternetConnected()) {
                AnalyticsResponseModel(message = "There is no access to the internet.", isError = true, errorType = ErrorType.InternetConnectionError)
            }else if (!Utils().isSmsPermissionGranted()) {
                AnalyticsResponseModel(
                    message = "Permission to read SMS messages from the device has been denied.",
                    isError = true,
                    errorType = ErrorType.SmsPermissionError
                )
            }
//            else if (!Utils().isLocationPermissionGranted()) {
//                AnalyticsResponseModel(
//                    message = "Permission to read the location of the device has been denied.",
//                    isError = true,
//                    errorType = ErrorType.LocationPermissionError
//                )
//            } else if (!Utils().isLocationEnabled()) {
//                AnalyticsResponseModel(
//                    message = "Location not enabled.",
//                    isError = true,
//                    errorType = ErrorType.LocationNotEnabledError
//                )
//            }
            else {
                AnalyticsResponseModel(
                    message = "An error occurred while submitting the request",
                    isError = true,
                    errorType = ErrorType.NetworkRequest
                )
            }
        }
    }


    internal suspend fun updateMobileInsightV2(
        publicKey: String,
        overviewKey: String,
        phoneNumber: String?,
        bvn: String?,
    ): AnalyticsResponseModel {

        return try {
            val response = RetrofitInstance.api.updateMobileInsightV2(
                overviewKey = overviewKey,
                analyticsBody = getAnalyticsData(phoneNumber!!, bvn!!, publicKey)
                )
            val data = response.execute()
            if(data.isSuccessful) {
                AnalyticsResponseModel(response = data.body()!!.toString(), isError = false)
            }else {
                when {
                    data.code() == 401 -> {
                        AnalyticsResponseModel(message = "Invalid Public key", isError = true, errorType = ErrorType.InvalidToken)
                    }
                    data.code() == 400 -> {
                        AnalyticsResponseModel(message = "There is already an Insights with the unique id provided. Please use the PATCH endpoint to update the existing Insights.", isError = true, errorType = ErrorType.InvalidToken)
                    }
                    else -> {
                        AnalyticsResponseModel(message = data.message(), isError = true, errorType = ErrorType.NetworkRequest)
                    }
                }
            }
        } catch (e: Exception) {
            if (!Utils().isInternetConnected()) {
                AnalyticsResponseModel(message = "There is no access to the internet.", isError = true, errorType = ErrorType.InternetConnectionError)
            }else if (!Utils().isSmsPermissionGranted()) {
                AnalyticsResponseModel(
                    message = "Permission to read SMS messages from the device has been denied.",
                    isError = true,
                    errorType = ErrorType.SmsPermissionError
                )
            }
//            else if (!Utils().isLocationPermissionGranted()) {
//                AnalyticsResponseModel(
//                    message = "Permission to read the location of the device has been denied.",
//                    isError = true,
//                    errorType = ErrorType.LocationPermissionError
//                )
//            } else if (!Utils().isLocationEnabled()) {
//                AnalyticsResponseModel(
//                    message = "Location not enabled.",
//                    isError = true,
//                    errorType = ErrorType.LocationNotEnabledError
//                )
//            }
            else {
                AnalyticsResponseModel(
                    message = "An error occurred while submitting the request",
                    isError = true,
                    errorType = ErrorType.NetworkRequest
                )
            }
        }
    }

    private suspend fun getAnalyticsData(
        phoneNumber: String,
        bvn: String,
        publicKey: String,
    ): AnalyticsModel {
        val packageManager = PericulumDependency.getApplicationContext().packageManager
        val packageInfo = packageManager.getPackageInfo(
            PericulumDependency.getApplicationContext().packageName,
            PackageManager.GET_PERMISSIONS
        )

        return AnalyticsModel(
            publicKey = publicKey,
            statementName = "${SimpleDateFormat("dd|MM|yyyy|HH|mm|ss|SSS|", Locale.getDefault()).format(System.currentTimeMillis())}|${Random.nextFloat()}|$phoneNumber",
            appName = PericulumDependency.getApplicationContext().getProjectName(),
            bundleId = PericulumDependency.getApplicationContext().packageName,
            version = packageInfo.versionName,
            device = getDeviceInfo(),
            sms = getSmsData(),
            metadata = MetadataModel(
                customer = CustomerModel(
                    phoneNumber = phoneNumber,
                    bvn = bvn
                )
            ),
//            location = locationModel
        )
    }


    private suspend fun getSmsData(): SmsModel {
        val smslist = SmsRepository().getSmsDataFromDevice()
        return SmsModel(
            data = smslist,
            count = smslist.size
        )
    }

    private fun getDeviceInfo(): DeviceModel {
        val packageManager = PericulumDependency.getApplicationContext().packageManager
        val packageInfo = packageManager.getPackageInfo(
            PericulumDependency.getApplicationContext().packageName,
            PackageManager.GET_PERMISSIONS
        )
        val batteryManager = PericulumDependency.getApplicationContext()
            .getSystemService(BATTERY_SERVICE) as BatteryManager
        val batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        //TODO:
        println(Build.MODEL)
        return DeviceModel(
            device = Build.DEVICE,
            deviceId = Build.ID,
            deviceName = Build.MODEL,
            firstInstallTime = packageInfo.firstInstallTime,
            baseOs = Build.VERSION.BASE_OS,
            apiLevel = Build.VERSION.SDK_INT,
            androidId = Utils().getDeviceUniqueId(),
            batteryLevel = batteryLevel,
            brand = Build.BRAND,
            buildNumber = packageInfo.versionCode,
            fingerprint = "google/sdk_gphone_x86/generic_x86:10/QSR1.200715.002/6695061:userdebug/dev-keys",
            manufacturer = Build.MANUFACTURER,
            maxMemory = Runtime.getRuntime().maxMemory(),
            readableVersion = packageInfo.versionName,
            uniqueId = Utils().getDeviceUniqueId(),
            isTablet = Utils().isTablet(),
            camera = getCameraDetails(),
            network = getNetworkDetails()
        )
    }

    private fun getCameraDetails(): CameraModel {
        val packageManager = PericulumDependency.getApplicationContext().packageManager
        return CameraModel(
            isCameraPresent = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY
            )
        )
    }

    private fun getNetworkDetails(): NetworkModel {
        val carrierName = (PericulumDependency.getApplicationContext()
            .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).networkOperatorName
        return NetworkModel(
            carrier = carrierName,
            ip = Utils().getDeviceIpAddress()!!,
            macAddress = Utils().getMACAddress("wlan0")!!
        )
    }
}