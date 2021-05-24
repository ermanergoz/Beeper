package com.erman.beeper.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erman.beeper.data.PreferenceProvider
import com.erman.beeper.model.AlarmModel

class MainViewModel(private val alarmModel: AlarmModel, private val preferenceProvider: PreferenceProvider) : ViewModel() {
    private val _isChecked = MutableLiveData<Boolean>().apply {
        value = preferenceProvider.getAlarmPreference()
    }
    val isChecked: LiveData<Boolean> = _isChecked

    fun onCheckedChanged(checked: Boolean) {
        preferenceProvider.setAlarmPreference(checked)
        updateAlarms()
    }

    fun updateAlarms() {
        if (preferenceProvider.getAlarmPreference()) alarmModel.scheduleAlarms()
        else alarmModel.cancelAlarms()
    }
}