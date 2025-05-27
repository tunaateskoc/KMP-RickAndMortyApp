package com.tunaateskoc.rickandmortyapp

interface SecurePreferencesRepository {
    fun <T> save(key: String, value: T)
    fun getString(key: String, defaultValue: String?): String?
    fun getInt(key: String, defaultValue: Int): Int?
    fun getBoolean(key: String, defaultValue: Boolean): Boolean?
    fun getFloat(key: String, defaultValue: Float): Float?
    fun getLong(key: String, defaultValue: Long): Long?
    fun remove(key: String)
    fun clear()
}