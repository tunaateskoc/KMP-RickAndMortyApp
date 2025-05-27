package com.tunaateskoc.rickandmortyapp.feature.domain.repository

import com.tunaateskoc.rickandmortyapp.feature.data.model.CharacterModel
import com.tunaateskoc.rickandmortyapp.feature.data.model.CharactersModel
import com.tunaateskoc.rickandmortyapp.feature.domain.datasource.local.CharacterLocalDataSource
import com.tunaateskoc.rickandmortyapp.feature.domain.datasource.remote.CharacterDataSourceInterface
import com.tunaateskoc.rickandmortyapp.feature.domain.mapper.CharacterMapperInterface
import com.tunaateskoc.rickandmortyapp.network.util.NetworkError
import com.tunaateskoc.rickandmortyapp.network.util.Result
import com.tunaateskoc.rickandmortyapp.network.util.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterRepository(
    private val characterDataSource: CharacterDataSourceInterface,
    private val characterLocalDataSource: CharacterLocalDataSource,
    private val characterMapper: CharacterMapperInterface
) : CharacterRepositoryInterface {

    override suspend fun getCharacters(page: Int): Result<CharactersModel, NetworkError> {
        return characterDataSource
            .getCharacters(page)
            .map { response ->
                characterMapper.mapCharacters(response)
            }
    }

    override suspend fun getCharacterDetail(id: Int): Result<CharacterModel, NetworkError> {
        return characterDataSource
            .getCharacterDetail(id)
            .map { character ->
                characterMapper.mapCharacterDetail(character)
            }
    }

    override suspend fun upsertCharacter(character: CharacterModel) {
        characterLocalDataSource.upsertCharacter(
            characterMapper.mapCharacterModel(character)
        )
    }

    override fun getFavoriteCharacters(): Flow<List<CharacterModel>> {
        return characterLocalDataSource
            .getFavoriteCharacters()
            .map { entityList ->
                entityList.map { entity ->
                    characterMapper.mapCharacterEntity(entity)
                }
            }
    }

    override fun isFavoriteCharacter(characterId: Int): Flow<Boolean> {
        return characterLocalDataSource.isFavoriteCharacter(characterId)
    }

    override suspend fun deleteFavoriteCharacter(id: Int) {
        characterLocalDataSource.deleteFavoriteCharacter(id)
    }
}