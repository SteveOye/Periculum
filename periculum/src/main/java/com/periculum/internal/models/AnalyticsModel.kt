package com.periculum.internal.models

internal data class AnalyticsModel (
    val statementName: String,
    val appName: String,
    val bundleId: String,
    val version: String,
    val device: DeviceModel,
    val sms: SmsModel,
    val metadata: MetadataModel,
    val location: LocationModel
)