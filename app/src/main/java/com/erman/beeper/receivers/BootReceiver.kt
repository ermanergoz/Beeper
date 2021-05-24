package com.erman.beeper.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {
    private val bootReceiverHelper: BootReceiverHelper by lazy { BootReceiverHelper() }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            bootReceiverHelper.onBootReceived(context, intent)
        }
    }
}