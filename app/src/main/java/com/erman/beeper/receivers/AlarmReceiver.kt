package com.erman.beeper.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.ToneGenerator
import com.erman.beeper.model.AlarmScheduler
import com.erman.beeper.utils.*

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        makeBeepSound(intent?.getStringExtra(KEY_ALARM_TYPE))
        reSetAlarms(context)
    }

    private fun makeBeepSound(beepType: String?) {
        val toneGen1 = ToneGenerator(AudioManager.STREAM_MUSIC, VOLUME_BEEP)

        if (beepType == KEY_ALARM_FULL_HOUR)
            toneGen1.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, DURATION_ALARM_FULL_HOUR)

        if (beepType == KEY_ALARM_HALF_HOUR)
            toneGen1.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, DURATION_ALARM_HALF_HOUR)
    }

    private fun reSetAlarms(context: Context) {
        val alarmScheduler = AlarmScheduler(context)
        alarmScheduler.setFullHourAlarm()
        alarmScheduler.setHalfHourAlarm()
    }
}