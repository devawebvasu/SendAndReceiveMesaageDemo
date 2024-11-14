package com.sendreceivemessagedemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.util.Log
import androidx.core.content.ContextCompat
import java.util.Objects

class MySmsReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {

        val bundle: Bundle? = p1?.extras

        if (bundle != null) {
            val objects = bundle.get("pdus") as Array<*>
            for (obj in objects) {
                val message: SmsMessage = SmsMessage.createFromPdu(obj as ByteArray)

                val mobNum = message.displayOriginatingAddress
                val msg = message.displayMessageBody

                Log.d("main", "MessageBody : $mobNum and sender is $msg")

                if (ContextCompat.checkSelfPermission(
                        p0!!,
                        android.Manifest.permission.SEND_SMS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    val smsManager = SmsManager.getDefault()

                    smsManager.sendTextMessage(
                        "+919898989898",
                        null,
                        "Hello.. How Are You?",
                        null,
                        null
                    )

                } else {
                    Log.e("main", "SEND_SMS permission not granted")
                }
            }
        }
    }
}