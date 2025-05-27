package com.tunaateskoc.rickandmortyapp.feature.domain.datasource.remote

import com.tunaateskoc.rickandmortyapp.feature.data.response.CharacterResponse
import com.tunaateskoc.rickandmortyapp.feature.data.response.CharactersResponse
import com.tunaateskoc.rickandmortyapp.network.util.NetworkError
import com.tunaateskoc.rickandmortyapp.network.util.Result

interface CharacterDataSourceInterface {
    suspend fun getCharacters(page: Int): Result<CharactersResponse?, NetworkError>
    suspend fun getCharacterDetail(id: Int): Result<CharacterResponse?, NetworkError>
}