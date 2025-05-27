package com.tunaateskoc.rickandmortyapp.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    data object CharacterListScreen : Screen

    @Serializable
    data class CharacterDetailScreen(
        val characterId: Int
    ) : Screen
}