package com.tunaateskoc.rickandmortyapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.tunaateskoc.rickandmortyapp.feature.data.model.CharacterModel

@Composable
fun CharacterItemView(
    modifier: Modifier = Modifier,
    character: CharacterModel,
    onCardClick: (characterId: Int) -> Unit = {},
    onFavoriteClick: (characterId: Int) -> Unit = {}
) {
    Card(
        modifier = modifier
            .padding(6.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onCardClick(character.id) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Box {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SubcomposeAsyncImage(
                    model = character.image,
                    contentDescription = character.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                    loading = {
                        CircularProgressIndicator(modifier = Modifier.requiredSize(40.dp))
                    }
                )

                Text(
                    text = character.name,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 6.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )

                StatusBadgeView(character.status)
            }

            FavoriteButtonView(
                modifier = Modifier.align(Alignment.TopEnd)
                    .padding(4.dp)
                    .size(32.dp),
                isFavorite = character.isFavorite,
                onButtonClicked = { onFavoriteClick(character.id) }
            )
        }
    }
}