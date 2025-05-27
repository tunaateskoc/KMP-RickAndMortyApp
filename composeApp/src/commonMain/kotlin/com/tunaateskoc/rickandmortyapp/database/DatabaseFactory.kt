package com.tunaateskoc.rickandmortyapp.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<RickAndMortyAppDatabase>
}