package com.erman.beeper

const val NOTIFICATION_ID = 1
const val NOTIFICATION_CHANNEL_ID = "Channel_Id"
const val BEEP_TIME_PERIOD: Long = 1500000  //25 minutes
const val ONE_MINUTE_PERIOD: Long = 1000
const val DOUBLE_BEEP_DURATION: Int = 400
const val SINGLE_BEEP_DURATION: Int = 250
const val SHARED_PREF_FILE: String = "com.erman.beeper"
const val PREFERENCE_CAN_BEEP: String = "can_beep"
const val CAN_BEEP_DEF_VALUE: Boolean = true
const val SIMPLE_DATE_FORMAT_PATTERN: String = "dd MMMM | HH:mm:ss"