package com.tunaateskoc.rickandmortyapp.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(
    private val context: Context
) {
    actual fun create(): RoomDatabase.Builder<RickAndMortyAppDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(RickAndMortyAppDatabase.DB_NAME)

        return Room.databaseBuilder(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}