package com.tunaateskoc.rickandmortyapp.feature.screen.detail

import com.tunaateskoc.rickandmortyapp.feature.data.model.CharacterModel

data class CharacterDetailViewState(
    val character: CharacterModel = CharacterModel()
)