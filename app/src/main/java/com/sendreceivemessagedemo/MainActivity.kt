package com.sendreceivemessagedemo

import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var smsReceiver: MySmsReceiver
    @RequiresApi(Build.VERSION_CODES.O)
    private val PERMISSION_REQUEST_CODE = 1

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SEND_SMS), PERMISSION_REQUEST_CODE)
        }

        smsReceiver = MySmsReceiver()

        var filter = IntentFilter("android:provider.Telephony.SMS_RECEIVED")

        registerReceiver(smsReceiver,filter, RECEIVER_NOT_EXPORTED)

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(smsReceiver)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                // Permission denied
            }
        }
    }
}