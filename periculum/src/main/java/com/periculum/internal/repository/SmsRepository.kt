package com.periculum.internal.repository

import android.database.Cursor
import android.net.Uri
import com.periculum.internal.models.SmsDataModel
import com.periculum.internal.utils.PericulumDependency
import java.util.*

internal class SmsRepository {

    internal suspend fun getSmsDataFromDevice() : List<SmsDataModel> {
        val smsList = mutableListOf<SmsDataModel>()
        val inboxURI: Uri = Uri.parse("content://sms/inbox")
        val calendar: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.add(Calendar.MONTH, -6)
        val lastSixMonthsInMillis: Long = calendar.timeInMillis
        val cursor: Cursor = PericulumDependency.getApplicationContext().contentResolver.query(inboxURI,
            arrayOf("_id", "thread_id", "address", "date", "date_sent", "protocol", "read", "status", "type", "reply_path_present", "body", "locked", "sub_id", "error_code", "creator", "seen"), "date>=$lastSixMonthsInMillis", null, null)!!

        while (cursor.moveToNext()) {
            val smsData = SmsDataModel(
                id = cursor.getString(0).toInt(),
                threadId = cursor.getString(1).toInt(),
                address = cursor.getString(2),
                date = cursor.getString(3).toLong(),
                dateSent = cursor.getString(4).toLong(),
                protocol = cursor.getString(5).toInt(),
                read = cursor.getString(6).toInt(),
                status = cursor.getString(7).toInt(),
                type = cursor.getString(8).toInt(),
                replyPathPresent = cursor.getString(9).toInt(),
                body = cursor.getString(10),
                locked = cursor.getString(11).toInt(),
                subId = cursor.getString(12).toInt(),
                errorCode = cursor.getString(13).toInt(),
                creator = cursor.getString(14),
                seen = cursor.getString(15).toInt(),
            )
            smsList.add(smsData)
        }
        cursor.close()

        return smsList
    }
}