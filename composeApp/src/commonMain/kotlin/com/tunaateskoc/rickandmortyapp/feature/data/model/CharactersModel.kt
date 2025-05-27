package com.tunaateskoc.rickandmortyapp.feature.data.model

import androidx.compose.runtime.Immutable

@Immutable
data class CharactersModel(
    val info: Info,
    val characters: List<CharacterModel>
)