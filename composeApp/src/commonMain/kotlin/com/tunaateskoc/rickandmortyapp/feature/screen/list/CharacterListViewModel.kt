package com.tunaateskoc.rickandmortyapp.feature.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tunaateskoc.rickandmortyapp.SecurePreferencesRepository
import com.tunaateskoc.rickandmortyapp.components.base.UiState
import com.tunaateskoc.rickandmortyapp.feature.domain.repository.CharacterRepositoryInterface
import com.tunaateskoc.rickandmortyapp.network.util.onError
import com.tunaateskoc.rickandmortyapp.network.util.onSuccess
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CharacterListViewModel(
    private val characterRepository: CharacterRepositoryInterface,
    private val securePreferencesRepository: SecurePreferencesRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(CharacterListViewState())
    val viewState = _viewState.asStateFlow()

    private val _navigateToCharacterDetailScreen =
        Channel<Int?>(Channel.BUFFERED)
    val navigateToCharacterDetailScreen = _navigateToCharacterDetailScreen.receiveAsFlow()

    private val _uiState =
        Channel<UiState>(Channel.BUFFERED)
    val uiState = _uiState.receiveAsFlow()

    init {
        getFavoriteCharactersFromLocal()
        fetchCharacters()
    }

    fun getFavoriteCharactersFromLocal() {
        viewModelScope.launch(Dispatchers.IO) {
            characterRepository.getFavoriteCharacters().collect { favoriteCharacters ->
                val favoriteIds = favoriteCharacters.map { it.id }.toSet()
                val characters = _viewState.value.characters.map { character ->
                    if (favoriteIds.contains(character.id)) {
                        character.copy(isFavorite = true)
                    } else {
                        character.copy(isFavorite = false)
                    }
                }
                _viewState.value = _viewState.value.copy(
                    characters = characters.toPersistentList(),
                    favoriteCharacters = favoriteCharacters.toPersistentList()
                )
            }
        }
    }

    fun fetchCharacters() {
        val viewState = _viewState.value
        if (viewState.isLoading || viewState.info.count > viewState.currentPage) return
        if (viewState.currentPage != 0) {
            _viewState.value = _viewState.value.copy(
                isLoading = true
            )
        } else {
            _uiState.trySend(UiState.ShowLoading)
        }

        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            characterRepository.getCharacters(viewState.currentPage.plus(1))
                .onSuccess {
                    val favoriteIds = _viewState.value.favoriteCharacters.map { it.id }.toSet()
                    val characters =
                        (_viewState.value.characters + it.characters).map { character ->
                            if (favoriteIds.contains(character.id)) {
                                character.copy(isFavorite = true)
                            } else {
                                character
                            }
                        }

                    _viewState.value = _viewState.value.copy(
                        characters = characters.toPersistentList(),
                        currentPage = _viewState.value.currentPage.plus(1),
                        isLoading = false
                    )
                    _uiState.trySend(UiState.ShowContent)
                }.onError {
                    _viewState.value = _viewState.value.copy(
                        isLoading = false,
                    )
                    _uiState.trySend(UiState.ShowSnackbar(it.name))
                }
        }
    }

    fun onFavoriteClicked(characterId: Int) {
        val currentState = _viewState.value

        val updatedCharacters = currentState.characters.map { character ->
            if (character.id == characterId) {
                viewModelScope.launch(Dispatchers.IO) {
                    if (character.isFavorite) {
                        characterRepository.deleteFavoriteCharacter(character.id)
                    } else {
                        characterRepository.upsertCharacter(character)
                    }
                }
                character.copy(isFavorite = !character.isFavorite)
            } else {
                character
            }
        }

        _viewState.value = currentState.copy(
            characters = updatedCharacters.toPersistentList()
        )
    }

    fun onCharacterClick(characterId: Int) {
        securePreferencesRepository.save("SAVED_CHARACTER_ID", characterId.toString())
        _navigateToCharacterDetailScreen.trySend(characterId)
    }
}