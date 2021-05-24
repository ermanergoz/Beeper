package com.erman.beeper.app.di

import android.content.Context
import com.erman.beeper.data.PreferenceProvider
import com.erman.beeper.model.AlarmModel
import com.erman.beeper.model.AlarmScheduler
import com.erman.beeper.utils.SHARED_PREF_FILE
import com.erman.beeper.view.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {
    factory { AlarmScheduler(get()) }
    factory { AlarmModel(get()) }
    viewModel { MainViewModel(get(), get()) }

    single {
        val context: Context = get()
        return@single context.getSharedPreferences(
            SHARED_PREF_FILE, Context.MODE_PRIVATE
        )
    }

    single { PreferenceProvider(get()) }
}