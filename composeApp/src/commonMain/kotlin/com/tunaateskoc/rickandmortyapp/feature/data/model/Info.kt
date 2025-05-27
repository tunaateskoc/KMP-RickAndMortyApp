package com.tunaateskoc.rickandmortyapp.feature.data.model

import androidx.compose.runtime.Immutable

@Immutable
data class Info(
    val count: Int = 0,
    val pages: Int = 0,
    val next: String = "",
    val prev: String = ""
)