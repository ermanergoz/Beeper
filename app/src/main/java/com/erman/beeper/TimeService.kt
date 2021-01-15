package com.erman.beeper

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import java.util.*

class TimeService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val toneGen1 = ToneGenerator(AudioManager.STREAM_MUSIC, 100)

        Thread(Runnable {
            while (true) {
                val minute = Calendar.getInstance().get(Calendar.MINUTE)
                if (getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE).getBoolean(PREFERENCE_CAN_BEEP, CAN_BEEP_DEF_VALUE)) {
                    if (minute == 12) {
                        toneGen1.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, DOUBLE_BEEP_DURATION)
                        Thread.sleep(BEEP_TIME_PERIOD)
                    }
                    if (minute == 30) {
                        toneGen1.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, SINGLE_BEEP_DURATION)
                        Thread.sleep(BEEP_TIME_PERIOD)
                    }
                }
                Thread.sleep(ONE_MINUTE_PERIOD)
            }
        }).start()

        startForeground()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun startForeground() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        createNotificationChannel()

        startForeground(
            NOTIFICATION_ID, NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setOngoing(true)
                .setSmallIcon(R.drawable.passing_time_icon)
                .setContentIntent(pendingIntent)
                .build()
        )
    }
}