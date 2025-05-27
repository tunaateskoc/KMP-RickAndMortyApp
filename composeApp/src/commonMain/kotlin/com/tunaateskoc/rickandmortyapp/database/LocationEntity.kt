package com.tunaateskoc.rickandmortyapp.database

import kotlinx.serialization.Serializable

@Serializable
data class LocationEntity(
    val name: String = "",
    val url: String = ""
)