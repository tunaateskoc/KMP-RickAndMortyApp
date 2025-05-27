package com.tunaateskoc.rickandmortyapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tunaateskoc.rickandmortyapp.feature.data.model.CharacterStatus
import org.jetbrains.compose.resources.stringResource
import rickandmortyapp.composeapp.generated.resources.Res
import rickandmortyapp.composeapp.generated.resources.status

private const val GREEN_COLOR = 0xFF4CAF50
private const val RED_COLOR = 0xFFF44336
private const val GRAY_COLOR = 0xFF9E9E9E

@Composable
fun StatusBadgeView(status: CharacterStatus) {
    val statusColor = when (status) {
        CharacterStatus.ALIVE -> Color(GREEN_COLOR)
        CharacterStatus.DEAD -> Color(RED_COLOR)
        else -> Color(GRAY_COLOR)
    }

    Text(
        text = "${stringResource(Res.string.status)} ${status.statusName}",
        color = Color.White,
        modifier = Modifier
            .padding(bottom = 6.dp)
            .background(statusColor, RoundedCornerShape(16.dp))
            .padding(horizontal = 10.dp, vertical = 2.dp),
        style = MaterialTheme.typography.labelSmall
    )
}