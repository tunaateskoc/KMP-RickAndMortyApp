package com.tunaateskoc.rickandmortyapp.feature.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterResponse(
    @SerialName("id") val id: Int?,
    @SerialName("name") val name: String?,
    @SerialName("status") val status: String?,
    @SerialName("species") val species: String?,
    @SerialName("type") val type: String?,
    @SerialName("gender") val gender: String?,
    @SerialName("origin") val origin: OriginResponse?,
    @SerialName("location") val location: LocationResponse?,
    @SerialName("image") val image: String?,
    @SerialName("episode") val episode: List<String?>?,
    @SerialName("url") val url: String?,
    @SerialName("created") val created: String?
)