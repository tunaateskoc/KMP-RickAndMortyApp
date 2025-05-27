package com.tunaateskoc.rickandmortyapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tunaateskoc.rickandmortyapp.feature.data.model.CharacterStatus

@Entity
data class CharacterEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val type: String,
    val gender: String,
    val origin: OriginEntity,
    val location: LocationEntity,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String,
    val isFavorite: Boolean
)