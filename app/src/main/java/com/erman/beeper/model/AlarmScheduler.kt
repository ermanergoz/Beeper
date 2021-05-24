package com.erman.beeper.model

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.erman.beeper.receivers.AlarmReceiver
import com.erman.beeper.utils.KEY_ALARM_FULL_HOUR
import com.erman.beeper.utils.KEY_ALARM_HALF_HOUR
import com.erman.beeper.utils.KEY_ALARM_TYPE
import java.util.*

class AlarmScheduler(private val context: Context) {
    private fun setAlarm(calendar: Calendar, intent: PendingIntent) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        when {
            //setRepeating causes problems in doze mode
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ->
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, intent)
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ->
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, intent)
            else ->
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, intent)
        }
    }

    fun setFullHourAlarm() {
        val fullHourAlarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            intent.putExtra(KEY_ALARM_TYPE, KEY_ALARM_FULL_HOUR)
            PendingIntent.getBroadcast(context, 0, intent, 0)
        }

        val calendarFullHour: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + 1)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        setAlarm(calendarFullHour, fullHourAlarmIntent)
    }

    fun setHalfHourAlarm() {
        val halfHourAlarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            intent.putExtra(KEY_ALARM_TYPE, KEY_ALARM_HALF_HOUR)
            PendingIntent.getBroadcast(context, 1, intent, 0)
        }

        val calendarHalfHour: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()

            if (Calendar.getInstance().get(Calendar.MINUTE) < 30) {
                set(Calendar.HOUR_OF_DAY, Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
                set(Calendar.MINUTE, 30)
                set(Calendar.SECOND, 0)
            } else {
                set(Calendar.HOUR_OF_DAY, Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + 1)
                set(Calendar.MINUTE, 30)
                set(Calendar.SECOND, 0)
            }
        }

        setAlarm(calendarHalfHour, halfHourAlarmIntent)
    }

    fun cancelAlarms() {
        val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, 0)
        })

        alarmManager.cancel(Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 1, intent, 0)
        })
    }
}