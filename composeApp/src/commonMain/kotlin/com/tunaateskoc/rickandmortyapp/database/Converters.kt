package com.tunaateskoc.rickandmortyapp.database

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object Converters {

    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromString(value: String): List<String> {
        return json.decodeFromString(value)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return json.encodeToString(list)
    }

    @TypeConverter
    fun fromOriginEntity(origin: OriginEntity): String {
        return json.encodeToString(origin)
    }

    @TypeConverter
    fun toOriginEntity(value: String): OriginEntity {
        return json.decodeFromString(value)
    }

    @TypeConverter
    fun fromLocationEntity(location: LocationEntity): String {
        return json.encodeToString(location)
    }

    @TypeConverter
    fun toLocationEntity(value: String): LocationEntity {
        return json.decodeFromString(value)
    }
}