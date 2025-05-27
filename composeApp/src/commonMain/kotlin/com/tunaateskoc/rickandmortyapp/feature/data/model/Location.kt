package com.tunaateskoc.rickandmortyapp.feature.data.model

import androidx.compose.runtime.Immutable

@Immutable
data class Location(
    val name: String = "",
    val url: String = ""
)