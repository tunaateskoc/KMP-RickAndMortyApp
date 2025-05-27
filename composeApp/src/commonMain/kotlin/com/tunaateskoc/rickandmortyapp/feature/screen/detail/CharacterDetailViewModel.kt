package com.tunaateskoc.rickandmortyapp.feature.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.navigation.toRoute
import com.tunaateskoc.rickandmortyapp.SecurePreferencesRepository
import com.tunaateskoc.rickandmortyapp.components.base.UiState
import com.tunaateskoc.rickandmortyapp.feature.domain.repository.CharacterRepositoryInterface
import com.tunaateskoc.rickandmortyapp.navigation.Screen
import com.tunaateskoc.rickandmortyapp.network.util.onError
import com.tunaateskoc.rickandmortyapp.network.util.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val characterRepository: CharacterRepositoryInterface,
    private val securePreferencesRepository: SecurePreferencesRepository
) : ViewModel() {

    private val characterId = savedStateHandle.toRoute<Screen.CharacterDetailScreen>().characterId

    private val _viewState = MutableStateFlow(CharacterDetailViewState())
    val viewState = _viewState.asStateFlow()

    private val _uiState =
        Channel<UiState>(Channel.BUFFERED)
    val uiState = _uiState.receiveAsFlow()

    init {
        println(securePreferencesRepository.getString("SAVED_CHARACTER_ID", null))
        fetchCharacterDetail(characterId)
    }

    private fun fetchCharacterDetail(characterId: Int) {
        _uiState.trySend(UiState.ShowLoading)
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            characterRepository.getCharacterDetail(characterId).onSuccess {
                characterRepository.isFavoriteCharacter(it.id).collect { isFavorite ->
                    val updatedCharacter = it.copy(isFavorite = isFavorite)
                    _viewState.value = _viewState.value.copy(
                        character = updatedCharacter
                    )
                    _uiState.trySend(UiState.ShowContent)
                }
            }.onError {
                _uiState.trySend(UiState.ShowSnackbar(it.name))
            }
        }
    }

    fun onFavoriteClicked() {
        val currentCharacter = _viewState.value.character

        viewModelScope.launch(Dispatchers.IO) {
            if (currentCharacter.isFavorite) {
                characterRepository.deleteFavoriteCharacter(currentCharacter.id)
            } else {
                characterRepository.upsertCharacter(currentCharacter)
            }
        }

        val updatedCharacter = currentCharacter.copy(
            isFavorite = !currentCharacter.isFavorite
        )

        _viewState.value = _viewState.value.copy(
            character = updatedCharacter
        )
    }
}