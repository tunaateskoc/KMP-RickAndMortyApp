package com.tunaateskoc.rickandmortyapp.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object RickAndMortyDatabaseConstructor: RoomDatabaseConstructor<RickAndMortyAppDatabase> {
    override fun initialize(): RickAndMortyAppDatabase
}