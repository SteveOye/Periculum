package com.periculum.internal.repository

import android.content.Context
import android.content.Context.BATTERY_SERVICE
import android.content.pm.PackageManager
import android.os.BatteryManager
import android.os.Build
import android.telephony.TelephonyManager
import android.util.Log
import com.google.gson.Gson
import com.periculum.internal.api.PericulumApi
import com.periculum.internal.models.*
import com.periculum.internal.utils.PericulumDependency
import com.periculum.internal.utils.Utils
import com.periculum.internal.utils.getAppName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random


internal class AnalyticsRepository {

    internal suspend fun postAnalyticsDataToServer(
        phoneNumber: String,
        bvn: String,
        token: String
    ): AnalyticsResponseModel? {

        val periculumApi = PericulumApi.create()
            .analytics(token = token, xTenant = "Nucleusis", analyticsBody = getAnalyticsData(phoneNumber, bvn))
        TODO("Try to Change the response to pure String rather than AnaylyticsResponseModel")
        try {
            return periculumApi.execute().body()!!
        }catch (e: Exception) {
            return null
        }
    }

    private suspend fun getAnalyticsData(phoneNumber: String, bvn: String): AnalyticsModel {
        val packageManager = PericulumDependency.getApplicationContext().packageManager
        val packageInfo = packageManager.getPackageInfo(
            PericulumDependency.getApplicationContext().packageName,
            PackageManager.GET_PERMISSIONS
        )
        return AnalyticsModel(
            statementName = "Kazeem ${Random.nextFloat()}",
            appName = PericulumDependency.getApplicationContext().getAppName(),
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
            location = LocationRepository().getUserLocationData()
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
        return CameraModel(isCameraPresent = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY))
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