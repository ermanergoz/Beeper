package com.erman.beeper

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BatteryOptimizationDialog.BatteryDialogListener {
    private lateinit var alarmScheduler: AlarmScheduler
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        alarmScheduler = AlarmScheduler(applicationContext)
        preferences = applicationContext.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)

        if (preferences.getBoolean(KEY_IS_ALARM_ON, true))
            scheduleAlarms()
        else
            cancelAlarms()

        alarmToggleButton.isChecked = preferences.getBoolean(KEY_IS_ALARM_ON, true)
        alarmToggleButton.setOnCheckedChangeListener { _, isChecked ->
            preferences.edit().putBoolean(KEY_IS_ALARM_ON, isChecked).apply()

            if (isChecked) scheduleAlarms()
            else cancelAlarms()
        }
    }

    override fun onResume() {
        super.onResume()

        if (!isIgnoringBatteryOptimizations())
            BatteryOptimizationDialog().show(supportFragmentManager, "")
    }

    private fun scheduleAlarms() {
        alarmScheduler.setFullHourAlarm()
        alarmScheduler.setHalfHourAlarm()
    }

    private fun cancelAlarms() {
        alarmScheduler.cancelAlarms()
    }

    private fun isIgnoringBatteryOptimizations(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val powerManager = applicationContext.getSystemService(Context.POWER_SERVICE) as PowerManager
            return powerManager.isIgnoringBatteryOptimizations(applicationContext.packageName)
        }
        return true
    }

    override fun navigateToSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent()
            intent.action = Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS
            startActivity(intent)
        }
    }
}