package com.tunaateskoc.rickandmortyapp.feature.data.model

import androidx.compose.runtime.Immutable

@Immutable
data class CharacterModel(
    val id: Int = 0,
    val name: String = "",
    val status: CharacterStatus = CharacterStatus.UNKNOWN,
    val species: String = "",
    val type: String = "",
    val gender: String = "",
    val origin: Origin = Origin(),
    val location: Location = Location(),
    val image: String = "",
    val episode: List<String> = emptyList(),
    val url: String = "",
    val created: String = "",
    val isFavorite: Boolean = false
)