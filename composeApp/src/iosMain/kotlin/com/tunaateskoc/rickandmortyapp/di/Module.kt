package com.tunaateskoc.rickandmortyapp.di

import com.tunaateskoc.rickandmortyapp.SecurePreferencesRepository
import com.tunaateskoc.rickandmortyapp.database.DatabaseFactory
import com.tunaateskoc.rickandmortyapp.securepreferences.IOSSecurePreferencesRepository
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module

@OptIn(ExperimentalForeignApi::class)
actual val platformModule: Module = module {
    single<HttpClientEngine> { Darwin.create() }
    single<SecurePreferencesRepository> { IOSSecurePreferencesRepository() }
    single { DatabaseFactory() }
}