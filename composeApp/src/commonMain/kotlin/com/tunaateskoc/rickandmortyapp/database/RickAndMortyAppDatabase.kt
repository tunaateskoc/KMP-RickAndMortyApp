package com.tunaateskoc.rickandmortyapp.database

import androidx.room.ConstructedBy
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
@ConstructedBy(
    RickAndMortyDatabaseConstructor::class
)
abstract class RickAndMortyAppDatabase : RoomDatabase() {

    abstract val favoriteCharacterDao: FavoriteCharacterDao

    companion object {
        const val DB_NAME = "rickAndMortyApp.db"
    }
}