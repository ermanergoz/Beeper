package com.erman.beeper

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences
    lateinit var preferencesEditor: SharedPreferences.Editor
    private val dateFormat = SimpleDateFormat(SIMPLE_DATE_FORMAT_PATTERN)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val model = ViewModelProvider(this).get(TimeChangedViewModel::class.java)
        model.timerValue.observe(this, androidx.lifecycle.Observer {
            textView.text = dateFormat.format(it)
        })

        preferences = this.getSharedPreferences(SHARED_PREF_FILE, AppCompatActivity.MODE_PRIVATE)

        toggleButton.isChecked=getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE).getBoolean(PREFERENCE_CAN_BEEP, CAN_BEEP_DEF_VALUE)

        toggleButton.setOnCheckedChangeListener { buttonView, isChecked ->
            preferencesEditor = preferences.edit()
            preferencesEditor.putBoolean(PREFERENCE_CAN_BEEP, isChecked)
            preferencesEditor.apply()
        }
    }
}