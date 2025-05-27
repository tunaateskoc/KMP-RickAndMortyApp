package com.tunaateskoc.rickandmortyapp.feature.domain.mapper

import com.tunaateskoc.rickandmortyapp.database.CharacterEntity
import com.tunaateskoc.rickandmortyapp.feature.data.model.CharacterModel
import com.tunaateskoc.rickandmortyapp.feature.data.model.CharactersModel
import com.tunaateskoc.rickandmortyapp.feature.data.response.CharacterResponse
import com.tunaateskoc.rickandmortyapp.feature.data.response.CharactersResponse

interface CharacterMapperInterface {
    fun mapCharacters(response: CharactersResponse?): CharactersModel
    fun mapCharacterDetail(characterResponse: CharacterResponse?): CharacterModel
    fun mapCharacterModel(model: CharacterModel): CharacterEntity
    fun mapCharacterEntity(entity: CharacterEntity): CharacterModel
}