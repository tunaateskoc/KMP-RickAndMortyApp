package com.tunaateskoc.rickandmortyapp.feature.domain.datasource.local

import com.tunaateskoc.rickandmortyapp.database.CharacterEntity
import com.tunaateskoc.rickandmortyapp.database.FavoriteCharacterDao
import kotlinx.coroutines.flow.Flow

class CharacterLocalDataSource(
    private val favoriteCharacterDao: FavoriteCharacterDao
) : CharacterLocalDataSourceInterface {

    override suspend fun upsertCharacter(character: CharacterEntity) {
        favoriteCharacterDao.upsert(character)
    }

    override fun getFavoriteCharacters(): Flow<List<CharacterEntity>> {
        return favoriteCharacterDao.getFavoriteCharacters()
    }

    override suspend fun deleteFavoriteCharacter(id: Int) {
        favoriteCharacterDao.deleteFavoriteCharacter(id)
    }

    override fun isFavoriteCharacter(characterId: Int): Flow<Boolean> {
        return favoriteCharacterDao.isFavoriteCharacter(characterId)
    }
}