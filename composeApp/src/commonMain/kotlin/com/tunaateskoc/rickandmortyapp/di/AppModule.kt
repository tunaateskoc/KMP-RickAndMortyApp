package com.tunaateskoc.rickandmortyapp.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.tunaateskoc.rickandmortyapp.database.DatabaseFactory
import com.tunaateskoc.rickandmortyapp.database.RickAndMortyAppDatabase
import com.tunaateskoc.rickandmortyapp.feature.domain.datasource.local.CharacterLocalDataSource
import com.tunaateskoc.rickandmortyapp.feature.domain.datasource.local.CharacterLocalDataSourceInterface
import com.tunaateskoc.rickandmortyapp.feature.domain.datasource.remote.CharacterDataSource
import com.tunaateskoc.rickandmortyapp.feature.domain.datasource.remote.CharacterDataSourceInterface
import com.tunaateskoc.rickandmortyapp.feature.domain.mapper.CharacterMapper
import com.tunaateskoc.rickandmortyapp.feature.domain.mapper.CharacterMapperInterface
import com.tunaateskoc.rickandmortyapp.feature.domain.repository.CharacterRepository
import com.tunaateskoc.rickandmortyapp.feature.domain.repository.CharacterRepositoryInterface
import com.tunaateskoc.rickandmortyapp.feature.screen.detail.CharacterDetailViewModel
import com.tunaateskoc.rickandmortyapp.feature.screen.list.CharacterListViewModel
import com.tunaateskoc.rickandmortyapp.network.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<RickAndMortyAppDatabase>().favoriteCharacterDao }

    viewModelOf(::CharacterListViewModel)
    viewModelOf(::CharacterDetailViewModel)

    singleOf(::CharacterRepository).bind<CharacterRepositoryInterface>()
    singleOf(::CharacterDataSource).bind<CharacterDataSourceInterface>()
    singleOf(::CharacterMapper).bind<CharacterMapperInterface>()
    singleOf(::CharacterLocalDataSource).bind<CharacterLocalDataSourceInterface>()
}