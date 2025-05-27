package com.tunaateskoc.rickandmortyapp.securepreferences

import android.content.SharedPreferences
import androidx.core.content.edit
import com.tunaateskoc.rickandmortyapp.SecurePreferencesRepository

class AndroidSecurePreferencesRepository(
    private val sharedPrefs: SharedPreferences
) : SecurePreferencesRepository {

    override fun <T> save(key: String, value: T) {
        sharedPrefs.edit {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
            }
        }
    }

    override fun getString(key: String, defaultValue: String?): String? {
        return sharedPrefs.getString(key, defaultValue) ?: defaultValue
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return sharedPrefs.getInt(key, defaultValue)
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPrefs.getBoolean(key, defaultValue)
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return sharedPrefs.getFloat(key, defaultValue)
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return sharedPrefs.getLong(key, defaultValue)
    }

    override fun remove(key: String) {
        sharedPrefs.edit { remove(key) }
    }

    override fun clear() {
        sharedPrefs.edit { clear() }
    }
}
