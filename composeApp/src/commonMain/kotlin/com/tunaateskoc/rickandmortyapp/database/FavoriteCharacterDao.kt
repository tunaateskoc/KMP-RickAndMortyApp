package com.tunaateskoc.rickandmortyapp.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCharacterDao {

    @Upsert
    suspend fun upsert(book: CharacterEntity)

    @Query("SELECT * FROM CharacterEntity")
    fun getFavoriteCharacters(): Flow<List<CharacterEntity>>

    @Query("DELETE FROM CharacterEntity WHERE id = :id")
    suspend fun deleteFavoriteCharacter(id: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM CharacterEntity WHERE id = :characterId)")
    fun isFavoriteCharacter(characterId: Int): Flow<Boolean>
}