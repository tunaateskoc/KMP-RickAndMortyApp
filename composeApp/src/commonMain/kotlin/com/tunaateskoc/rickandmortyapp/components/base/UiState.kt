package com.tunaateskoc.rickandmortyapp.components.base

sealed interface UiState {
    data object ShowLoading : UiState
    data class ShowSnackbar(val text: String) : UiState
    data object ShowContent: UiState
}