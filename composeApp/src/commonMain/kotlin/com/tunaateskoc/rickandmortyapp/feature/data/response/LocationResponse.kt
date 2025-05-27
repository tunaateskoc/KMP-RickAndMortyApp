package com.tunaateskoc.rickandmortyapp.feature.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationResponse(
    @SerialName("name") val name: String?,
    @SerialName("url") val url: String?
)