package com.periculum.internal.models

data class SmsModel(
    val id : Int,
    val threadId: Int,
    val address: String,
    val date: Long,
    val dateSent: Long,
    val protocol: Int,
    val read: Int,
    val status: Int,
    val type: Int,
    val replyPathPresent: Int,
    val body: String,
    val locked: Int,
    val subId: Int,
    val errorCode: Int,
    val creator: String,
    val seen: Int
)