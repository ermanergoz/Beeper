package com.erman.beeper.data

import android.content.SharedPreferences
import com.erman.beeper.utils.KEY_IS_ALARM_ON

class PreferenceProvider (private val sharedPreferences: SharedPreferences) {
    fun setAlarmPreference(choice: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_ALARM_ON, choice).apply()
    }

    fun getAlarmPreference(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_ALARM_ON, true)
    }
}