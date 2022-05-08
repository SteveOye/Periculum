package com.periculum.internal.models

import com.google.gson.annotations.SerializedName

data class ClientData (
    val statementKey: Long? = null,
    val identificationData: List<ClientIdentification>? = null
)