package com.tunaateskoc.rickandmortyapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [CharacterEntity::class],
    version = 1
)
@TypeConverters(
    Converters::class
)
abstract class RickAndMortyAppDatabase : RoomDatabase() {

    abstract val favoriteCharacterDao: FavoriteCharacterDao

    companion object {
        const val DB_NAME = "rickAndMortyApp.db"
    }
}