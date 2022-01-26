package com.example.mailbox.service

import android.content.Context

class PreferencesProvider(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("mailPreferences", 0)

    //Save boolean values into the shared preferences
    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    //Retrieve boolean values from the shared preferences
    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    //For String values
    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    //For Integer values
    fun putInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    //Clear shared preferences
    fun clear() {
        sharedPreferences.edit().clear().apply() //this will be clear whole shared preferences data
    }

}