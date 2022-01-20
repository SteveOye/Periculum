package com.periculum.internal.models

internal data class DeviceModel(
    val device: String,
    val deviceId: String,
    val deviceName: String,
    val firstInstallTime: Long,
    val baseOs: String,
    val apiLevel: Int,
    val androidId: String,
    val batteryLevel: Int,
    val brand: String,
    val buildNumber: Int,
    val fingerprint: String,
    val manufacturer: String,
    val maxMemory: Long,
    val readableVersion: String,
    val uniqueId: String,
    val isTablet: Boolean,
    val camera: CameraModel,
    val network: NetworkModel,

)
