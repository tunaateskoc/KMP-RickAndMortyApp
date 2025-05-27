package com.tunaateskoc.rickandmortyapp.feature.screen.list

import androidx.compose.runtime.Immutable
import com.tunaateskoc.rickandmortyapp.feature.data.model.CharacterModel
import com.tunaateskoc.rickandmortyapp.feature.data.model.Info
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class CharacterListViewState(
    val characters: PersistentList<CharacterModel> = persistentListOf(),
    val favoriteCharacters: PersistentList<CharacterModel> = persistentListOf(),
    val info: Info = Info(),
    val currentPage: Int = 0,
    val isLoading: Boolean = false
)
