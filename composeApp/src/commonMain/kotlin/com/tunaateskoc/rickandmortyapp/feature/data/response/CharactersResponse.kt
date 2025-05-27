package com.tunaateskoc.rickandmortyapp.feature.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharactersResponse(
    @SerialName("info") val infoResponse: InfoResponse?,
    @SerialName("results") val results: List<CharacterResponse?>?
)
