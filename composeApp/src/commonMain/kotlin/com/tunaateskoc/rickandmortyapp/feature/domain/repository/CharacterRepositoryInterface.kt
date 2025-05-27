package com.tunaateskoc.rickandmortyapp.feature.domain.repository

import com.tunaateskoc.rickandmortyapp.feature.data.model.CharacterModel
import com.tunaateskoc.rickandmortyapp.feature.data.model.CharactersModel
import com.tunaateskoc.rickandmortyapp.network.util.NetworkError
import com.tunaateskoc.rickandmortyapp.network.util.Result
import kotlinx.coroutines.flow.Flow

interface CharacterRepositoryInterface {
    suspend fun getCharacters(page: Int): Result<CharactersModel, NetworkError>
    suspend fun getCharacterDetail(id: Int): Result<CharacterModel, NetworkError>
    suspend fun upsertCharacter(character: CharacterModel)
    fun getFavoriteCharacters(): Flow<List<CharacterModel>>
    fun isFavoriteCharacter(characterId: Int): Flow<Boolean>
    suspend fun deleteFavoriteCharacter(id: Int)
}