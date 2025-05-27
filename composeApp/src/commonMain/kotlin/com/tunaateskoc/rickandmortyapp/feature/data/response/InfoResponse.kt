package com.tunaateskoc.rickandmortyapp.feature.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InfoResponse(
    @SerialName("count") val count: Int?,
    @SerialName("pages") val pages: Int?,
    @SerialName("next") val next: String?,
    @SerialName("prev") val prev: String?
)