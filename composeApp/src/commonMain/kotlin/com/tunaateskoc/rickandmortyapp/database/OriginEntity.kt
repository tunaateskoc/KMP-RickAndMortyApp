package com.tunaateskoc.rickandmortyapp.database

import kotlinx.serialization.Serializable

@Serializable
data class OriginEntity(
    val name: String = "",
    val url: String = ""
)