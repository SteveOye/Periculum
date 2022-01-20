package com.periculum.internal.models

internal data class SmsModel(
    val data: List<SmsDataModel>,
    val count: Int
)
