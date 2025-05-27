package com.tunaateskoc.rickandmortyapp.feature.screen.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.tunaateskoc.rickandmortyapp.components.CharacterItemView
import com.tunaateskoc.rickandmortyapp.components.base.BaseScreen
import com.tunaateskoc.rickandmortyapp.components.base.UiState
import com.tunaateskoc.rickandmortyapp.navigation.Screen
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import rickandmortyapp.composeapp.generated.resources.Res
import rickandmortyapp.composeapp.generated.resources.characters
import rickandmortyapp.composeapp.generated.resources.favorite_character_not_found
import rickandmortyapp.composeapp.generated.resources.favorites

@Composable
fun CharacterListScreen(
    navController: NavHostController,
    viewModel: CharacterListViewModel = koinViewModel()
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(UiState.ShowContent)
    val navigateToCharacterDetailScreen by viewModel.navigateToCharacterDetailScreen
        .collectAsStateWithLifecycle(null)

    val tabs = listOf(Res.string.characters, Res.string.favorites)
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()
    val backStackEntry = rememberUpdatedState(navController.currentBackStackEntry)

    BaseScreen(
        navController = navController,
        uiState = uiState
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TabRow(selectedTabIndex = pagerState.currentPage) {
                tabs.forEachIndexed { index, titleResId ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = { Text(text = stringResource(titleResId)) }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                when (page) {
                    0 -> CharacterListContent(
                        viewState = viewState,
                        onCardClick = { characterId ->
                            viewModel.onCharacterClick(characterId)
                        },
                        onFavoriteClick = { characterId ->
                            viewModel.onFavoriteClicked(characterId)
                        },
                        onScrollEnd = {
                            viewModel.fetchCharacters()
                        }
                    )

                    1 -> CharacterFavoriteContent(
                        viewState = viewState,
                        onCardClick = { characterId ->
                            viewModel.onCharacterClick(characterId)
                        },
                        onFavoriteClick = { characterId ->
                            viewModel.onFavoriteClicked(characterId)
                        }
                    )
                }
            }
        }
    }

    navigateToCharacterDetailScreen?.let {
        navController.navigate(
            Screen.CharacterDetailScreen(
                characterId = it
            )
        )
    }

    LaunchedEffect(backStackEntry.value) {
        viewModel.getFavoriteCharactersFromLocal()
    }
}

@Composable
fun CharacterListContent(
    viewState: CharacterListViewState,
    onCardClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    onScrollEnd: () -> Unit
) {
    val lazyGridState = rememberLazyGridState()
    val shouldLoadMore = remember {
        derivedStateOf {
            lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == lazyGridState.layoutInfo.totalItemsCount - 1
        }
    }

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        state = lazyGridState
    ) {
        items(viewState.characters.size) {
            CharacterItemView(
                modifier = Modifier.fillMaxWidth(),
                character = viewState.characters[it],
                onCardClick = { characterId ->
                    onCardClick(characterId)
                },
                onFavoriteClick = { characterId ->
                    onFavoriteClick(characterId)
                },
            )
        }
        if (viewState.isLoading) {
            item(span = { GridItemSpan(3) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value && !viewState.isLoading) {
            onScrollEnd()
        }
    }
}

@Composable
fun CharacterFavoriteContent(
    viewState: CharacterListViewState,
    onCardClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit
) {
    val lazyGridState = rememberLazyGridState()
    val favoriteCharacters = viewState.characters.filter { it.isFavorite }

    if (favoriteCharacters.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(Res.string.favorite_character_not_found),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    } else {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            state = lazyGridState
        ) {
            items(favoriteCharacters.size) {
                CharacterItemView(
                    modifier = Modifier.fillMaxWidth(),
                    character = favoriteCharacters[it],
                    onCardClick = { characterId ->
                        onCardClick(characterId)
                    },
                    onFavoriteClick = { characterId ->
                        onFavoriteClick(characterId)
                    },
                )
            }
        }
    }
}