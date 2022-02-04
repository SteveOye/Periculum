package com.periculum.internal.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import kotlin.math.pow
import kotlin.math.sqrt
import android.text.TextUtils

import androidx.test.core.app.ApplicationProvider.getApplicationContext

import android.net.wifi.WifiManager
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import java.lang.Exception
import java.lang.StringBuilder
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.*


internal class Utils {

    internal fun isSmsPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            PericulumDependency.getApplicationContext(),
            Manifest.permission.READ_SMS
        ) == PackageManager.PERMISSION_GRANTED
    }

    internal fun isLocationPermissionGranted(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return ContextCompat.checkSelfPermission(
                PericulumDependency.getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                PericulumDependency.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                PericulumDependency.getApplicationContext(),
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            return ContextCompat.checkSelfPermission(
                PericulumDependency.getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                PericulumDependency.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    internal fun isLocationEnabled(): Boolean {
        val locationManager = PericulumDependency.getApplicationContext()
            .getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }

    /**
     * This method is used to get the connection of a device
     * @param context Application context
     * @return It returns an int
    0: No Internet available (maybe on airplane mode, or in the process of joining an wi-fi).

    1: Cellular (mobile data, 3G/4G/LTE whatever).

    2: Wi-fi.

    3: VPN
     */
    private fun getConnectionType(): Int {
        var result = 0 // Returns connection type. 0: none; 1: mobile data; 2: wifi
        val cm = PericulumDependency.getApplicationContext()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        cm?.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        result = 2
                    }
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        result = 1
                    }
                    hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> {
                        result = 3
                    }
                }
            }
        }
        return result
    }

    internal fun isInternetConnected(): Boolean {
        return getConnectionType() != 0
    }

    @SuppressLint("HardwareIds")
    internal fun getDeviceUniqueId(): String {
        return Settings.Secure.getString(
            PericulumDependency.getApplicationContext().contentResolver,
            Settings.Secure.ANDROID_ID
        )
            ?: "99" +
            Build.BOARD.length % 10 + Build.DISPLAY.length % 10 + Build.HOST.length % 10 + Build.ID.length % 10 + Build.MANUFACTURER.length % 10 + Build.MODEL.length % 10 + Build.PRODUCT.length % 10 + Build.TAGS.length % 10 + Build.BRAND.length % 10 + Build.DEVICE.length % 10 + Build.TYPE.length % 10 + Build.USER.length % 10
    }

    internal fun isTablet(): Boolean {
        val dm: DisplayMetrics =
            PericulumDependency.getApplicationContext().resources.displayMetrics
        val screenWidth = dm.widthPixels / dm.xdpi
        val screenHeight = dm.heightPixels / dm.ydpi
        val size = sqrt(
            screenWidth.toDouble().pow(2.0) +
                    screenHeight.toDouble().pow(2.0)
        )
        return size >= 6
    }

    internal fun getDeviceIpAddress(): String? {
        var actualConnectedToNetwork: String? = null
        val connManager = PericulumDependency.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (connManager != null) {
            val mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            if (mWifi!!.isConnected) {
                actualConnectedToNetwork = getWifiIp()
            }
        }
        if (TextUtils.isEmpty(actualConnectedToNetwork)) {
            actualConnectedToNetwork = getNetworkInterfaceIpAddress()
        }
        if (TextUtils.isEmpty(actualConnectedToNetwork)) {
            actualConnectedToNetwork = "127.0.0.1"
        }
        return actualConnectedToNetwork
    }

    private fun getWifiIp(): String? {
        val mWifiManager = ApplicationProvider.getApplicationContext<Context>().getSystemService(
            Context.WIFI_SERVICE
        ) as WifiManager
        if (mWifiManager.isWifiEnabled) {
            val ip = mWifiManager.connectionInfo.ipAddress
            return ((ip and 0xFF).toString() + "." + (ip shr 8 and 0xFF) + "." + (ip shr 16 and 0xFF) + "."
                    + (ip shr 24 and 0xFF))
        }
        return null
    }

    private fun getNetworkInterfaceIpAddress(): String? {
        try {
            val en: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val networkInterface: NetworkInterface = en.nextElement()
                val enumIpAddr: Enumeration<InetAddress> = networkInterface.getInetAddresses()
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress: InetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress() && inetAddress is Inet4Address) {
                        val host: String = inetAddress.getHostAddress()
                        if (!TextUtils.isEmpty(host)) {
                            return host
                        }
                    }
                }
            }
        } catch (ex: Exception) {
//            Log.e("IP Address", "getLocalIpAddress", ex)
        }
        return null
    }

    /**
     * Returns MAC address of the given interface name.
     * @param interfaceName eth0, wlan0 or NULL=use first interface
     * @return  mac address or empty string
     */
    fun getMACAddress(interfaceName: String?): String? {
        try {
            val interfaces: List<NetworkInterface> =
                Collections.list(NetworkInterface.getNetworkInterfaces())
            for (data in interfaces) {
                if (interfaceName != null) {
                    if (!data.name.equals(interfaceName, ignoreCase = true)) continue
                }
                val mac = data.hardwareAddress ?: return ""
                val buf = StringBuilder()
                for (aMac in mac) buf.append(String.format("%02X:", aMac))
                if (buf.length > 0) buf.deleteCharAt(buf.length - 1)
                return buf.toString()
            }
        } catch (ignored: Exception) {
        }
        return "02:00:00:00:00:00"
    }

    companion object {
        internal const val baseUrl = "https://api.insights-periculum.com"
    }
}

fun Context.getProjectName(): String = applicationInfo.loadLabel(packageManager).toString()