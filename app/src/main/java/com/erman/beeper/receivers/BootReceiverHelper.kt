package com.erman.beeper.receivers

import android.content.Context
import android.content.Intent
import com.erman.beeper.data.PreferenceProvider
import com.erman.beeper.model.AlarmScheduler
import org.koin.core.KoinComponent
import org.koin.core.inject

class BootReceiverHelper : KoinComponent {
    private val preferenceProvider: PreferenceProvider by inject()
    private val alarmScheduler: AlarmScheduler by inject()

    fun onBootReceived(context: Context?, intent: Intent?) {
        if (preferenceProvider.getAlarmPreference()) {
            alarmScheduler.setFullHourAlarm()
            alarmScheduler.setHalfHourAlarm()
        }
    }
}