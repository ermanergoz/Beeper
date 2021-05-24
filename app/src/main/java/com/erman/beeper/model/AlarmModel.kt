package com.erman.beeper.model

class AlarmModel(private val alarmScheduler: AlarmScheduler) {
    fun scheduleAlarms() {
        alarmScheduler.setFullHourAlarm()
        alarmScheduler.setHalfHourAlarm()
    }

    fun cancelAlarms() {
        alarmScheduler.cancelAlarms()
    }
}