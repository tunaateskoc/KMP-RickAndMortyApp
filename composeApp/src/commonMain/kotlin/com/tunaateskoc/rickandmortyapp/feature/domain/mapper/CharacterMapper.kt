package com.tunaateskoc.rickandmortyapp.feature.domain.mapper

import com.tunaateskoc.rickandmortyapp.database.CharacterEntity
import com.tunaateskoc.rickandmortyapp.database.LocationEntity
import com.tunaateskoc.rickandmortyapp.database.OriginEntity
import com.tunaateskoc.rickandmortyapp.feature.data.model.CharacterModel
import com.tunaateskoc.rickandmortyapp.feature.data.model.CharacterStatus
import com.tunaateskoc.rickandmortyapp.feature.data.model.CharactersModel
import com.tunaateskoc.rickandmortyapp.feature.data.model.Info
import com.tunaateskoc.rickandmortyapp.feature.data.model.Location
import com.tunaateskoc.rickandmortyapp.feature.data.model.Origin
import com.tunaateskoc.rickandmortyapp.feature.data.response.CharacterResponse
import com.tunaateskoc.rickandmortyapp.feature.data.response.CharactersResponse

class CharacterMapper : CharacterMapperInterface {

    override fun mapCharacters(response: CharactersResponse?): CharactersModel {
        return CharactersModel(
            characters = response?.results?.mapNotNull { character ->
                mapCharacterDetail(character)
            }.orEmpty(),
            info = Info(
                count = response?.infoResponse?.count ?: 0,
                pages = response?.infoResponse?.pages ?: 0,
                next = response?.infoResponse?.next.orEmpty(),
                prev = response?.infoResponse?.prev.orEmpty()
            )
        )
    }

    override fun mapCharacterDetail(characterResponse: CharacterResponse?): CharacterModel {
        return CharacterModel(
            id = characterResponse?.id ?: 0,
            name = characterResponse?.name.orEmpty(),
            status = CharacterStatus.fromString(characterResponse?.status.orEmpty()),
            species = characterResponse?.species.orEmpty(),
            type = characterResponse?.type.orEmpty(),
            gender = characterResponse?.gender.orEmpty(),
            origin = Origin(
                name = characterResponse?.origin?.name.orEmpty(),
                url = characterResponse?.origin?.url.orEmpty(),
            ),
            location = Location(
                name = characterResponse?.location?.name.orEmpty(),
                url = characterResponse?.location?.url.orEmpty(),
            ),
            image = characterResponse?.image.orEmpty(),
            episode = characterResponse?.episode?.mapNotNull { it } ?: emptyList(),
            created = characterResponse?.created.orEmpty(),
            url = characterResponse?.url.orEmpty()
        )
    }

    override fun mapCharacterModel(model: CharacterModel): CharacterEntity {
        return CharacterEntity(
            id = model.id,
            name = model.name,
            status = model.status,
            species = model.species,
            type = model.type,
            gender = model.gender,
            origin = OriginEntity(
                name = model.origin.name,
                url = model.origin.url
            ),
            location = LocationEntity(
                name = model.location.name,
                url = model.location.url
            ),
            image = model.image,
            episode = model.episode,
            url = model.url,
            created = model.created,
            isFavorite = model.isFavorite
        )
    }

    override fun mapCharacterEntity(entity: CharacterEntity): CharacterModel {
        return CharacterModel(
            id = entity.id,
            name = entity.name,
            status = entity.status,
            species = entity.species,
            type = entity.type,
            gender = entity.gender,
            origin = Origin(
                name = entity.origin.name,
                url = entity.origin.url
            ),
            location = Location(
                name = entity.location.name,
                url = entity.location.url
            ),
            image = entity.image,
            episode = entity.episode,
            url = entity.url,
            created = entity.created,
            isFavorite = entity.isFavorite
        )
    }
}