package com.tunaateskoc.rickandmortyapp.feature.data.model

import androidx.compose.runtime.Immutable

@Immutable
data class Origin(
    val name: String = "",
    val url: String = ""
)