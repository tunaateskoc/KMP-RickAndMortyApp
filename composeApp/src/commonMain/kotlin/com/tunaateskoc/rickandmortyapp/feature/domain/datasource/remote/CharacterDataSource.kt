package com.tunaateskoc.rickandmortyapp.feature.domain.datasource.remote

import com.tunaateskoc.rickandmortyapp.feature.data.response.CharacterResponse
import com.tunaateskoc.rickandmortyapp.feature.data.response.CharactersResponse
import com.tunaateskoc.rickandmortyapp.getBaseUrl
import com.tunaateskoc.rickandmortyapp.network.safeCall
import com.tunaateskoc.rickandmortyapp.network.util.NetworkError
import com.tunaateskoc.rickandmortyapp.network.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class CharacterDataSource(
    private val httpClient: HttpClient
) : CharacterDataSourceInterface {

    override suspend fun getCharacters(page: Int): Result<CharactersResponse?, NetworkError> {
        return safeCall {
            httpClient.get(
                urlString = "${getBaseUrl()}/character/?page=$page"
            )
        }
    }

    override suspend fun getCharacterDetail(id: Int): Result<CharacterResponse?, NetworkError> {
        return safeCall {
            httpClient.get(
                urlString = "${getBaseUrl()}/character/$id"
            )
        }
    }
}