package com.tunaateskoc.rickandmortyapp.feature.domain.datasource.local

import com.tunaateskoc.rickandmortyapp.database.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface CharacterLocalDataSourceInterface {
    suspend fun upsertCharacter(character: CharacterEntity)
    fun getFavoriteCharacters(): Flow<List<CharacterEntity>>
    suspend fun deleteFavoriteCharacter(id: Int)
    fun isFavoriteCharacter(characterId: Int): Flow<Boolean>
}