package com.example.MySpy.data

import android.content.Context

class SharedPreferencesManager(applicationContext: Context) {

    val PREFS_FILENAME = "com.example.MySpy.prefs"
    val PIN_CODE = "com.example.MySpy.prefs"
    val sharedPreferences = applicationContext.getSharedPreferences(PREFS_FILENAME, 0)

    val hasPin: Boolean get() = getPin() > -1

    fun getPin(): Int {
        return sharedPreferences.getInt(PIN_CODE, -1)
    }

    fun setPin(value: Int) {
        sharedPreferences.edit().putInt(PIN_CODE, value).apply()
    }

}