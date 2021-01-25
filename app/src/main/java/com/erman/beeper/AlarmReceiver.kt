package com.erman.beeper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.ToneGenerator

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        makeBeepSound(intent?.getStringExtra(KEY_ALARM_TYPE))
        reSetAlarms(context)
    }

    private fun makeBeepSound(beepType: String?) {
        val toneGen1 = ToneGenerator(AudioManager.STREAM_MUSIC, 100)

        if (beepType == KEY_ALARM_FULL_HOUR)
            toneGen1.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 500)

        if (beepType == KEY_ALARM_HALF_HOUR)
            toneGen1.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 250)
    }

    private fun reSetAlarms(context: Context) {
        val alarmScheduler = AlarmScheduler(context)
        alarmScheduler.setFullHourAlarm()
        alarmScheduler.setHalfHourAlarm()
    }
}