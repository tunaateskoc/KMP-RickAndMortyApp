package com.tunaateskoc.rickandmortyapp.feature.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.tunaateskoc.rickandmortyapp.components.FavoriteButtonView
import com.tunaateskoc.rickandmortyapp.components.InfoRowView
import com.tunaateskoc.rickandmortyapp.components.StatusBadgeView
import com.tunaateskoc.rickandmortyapp.components.base.BaseScreen
import com.tunaateskoc.rickandmortyapp.components.base.UiState
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import rickandmortyapp.composeapp.generated.resources.Res
import rickandmortyapp.composeapp.generated.resources.created
import rickandmortyapp.composeapp.generated.resources.gender
import rickandmortyapp.composeapp.generated.resources.location
import rickandmortyapp.composeapp.generated.resources.origin
import rickandmortyapp.composeapp.generated.resources.species
import rickandmortyapp.composeapp.generated.resources.type

@Composable
fun CharacterDetailScreen(
    navController: NavHostController,
    viewModel: CharacterDetailViewModel = koinViewModel()
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(UiState.ShowContent)

    BaseScreen(
        navController = navController,
        isBackButtonVisible = true,
        uiState = uiState
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    AsyncImage(
                        model = viewState.character.image,
                        contentDescription = viewState.character.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .align(Alignment.Center)
                    )
                    FavoriteButtonView(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(40.dp),
                        isFavorite = viewState.character.isFavorite,
                        onButtonClicked = {
                            viewModel.onFavoriteClicked()
                        }
                    )
                }
            }

            if (viewState.character.name.isNotEmpty()) {
                item {
                    Text(
                        text = viewState.character.name,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }

            item {
                StatusBadgeView(viewState.character.status)
            }

            if (viewState.character.species.isNotEmpty()) {
                item {
                    InfoRowView(
                        stringResource(Res.string.species),
                        viewState.character.species
                    )
                }
            }

            if (viewState.character.type.isNotEmpty()) {
                item { InfoRowView(stringResource(Res.string.type), viewState.character.type) }
            }

            if (viewState.character.gender.isNotEmpty()) {
                item { InfoRowView(stringResource(Res.string.gender), viewState.character.gender) }
            }

            if (viewState.character.origin.name.isNotEmpty()) {
                item {
                    InfoRowView(
                        stringResource(Res.string.origin),
                        viewState.character.origin.name
                    )
                }
            }

            if (viewState.character.location.name.isNotEmpty()) {
                item {
                    InfoRowView(
                        stringResource(Res.string.location),
                        viewState.character.location.name
                    )
                }
            }

            if (viewState.character.created.isNotEmpty()) {
                item {
                    InfoRowView(
                        stringResource(Res.string.created),
                        viewState.character.created
                    )
                }
            }
        }
    }
}