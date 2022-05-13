package com.periculum.internal.models

import com.google.gson.annotations.SerializedName

internal data class SmsDataModel(
    @SerializedName("_id")
    var id : Int,
    @SerializedName("thread_id")
    val threadId: Int,
    @SerializedName("address")
    val address: String,
    @SerializedName("date")
    val date: Long,
    @SerializedName("date_sent")
    val dateSent: Long,
    @SerializedName("protocol")
    val protocol: Int,
    @SerializedName("read")
    val read: Int,
    @SerializedName("status")
    val status: Int,
    @SerializedName("type")
    val type: Int,
    @SerializedName("reply_path_present")
    val replyPathPresent: Int,
    @SerializedName("body")
    val body: String,
    @SerializedName("locked")
    val locked: Int,
    @SerializedName("sub_id")
    val subId: Int,
    @SerializedName("error_code")
    val errorCode: Int,
    @SerializedName("creator")
    val creator: String?,
    @SerializedName("seen")
    val seen: Int
)