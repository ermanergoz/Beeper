package com.erman.beeper

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit

class TimeChangedViewModel : ViewModel() {
    val timerValue = MutableLiveData<Date>()

    init {
        timerValue.value = Calendar.getInstance().time
        start()
    }

    private fun start() {
        Observable.interval(1, 1, TimeUnit.SECONDS)
            .subscribe({
                timerValue.postValue(Calendar.getInstance().time)
            }, Throwable::printStackTrace)
    }
}