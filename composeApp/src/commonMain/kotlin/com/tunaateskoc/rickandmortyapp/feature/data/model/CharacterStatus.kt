package com.tunaateskoc.rickandmortyapp.feature.data.model

enum class CharacterStatus(val statusName: String) {
    ALIVE("Alive"),
    DEAD("Dead"),
    UNKNOWN("Unknown");

    companion object {
        fun fromString(status: String): CharacterStatus {
            return when (status.lowercase()) {
                "alive" -> ALIVE
                "dead" -> DEAD
                else -> UNKNOWN
            }
        }
    }
}