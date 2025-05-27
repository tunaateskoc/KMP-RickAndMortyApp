package com.tunaateskoc.rickandmortyapp.di

import android.content.SharedPreferences
import com.tunaateskoc.rickandmortyapp.SecurePreferencesRepository
import com.tunaateskoc.rickandmortyapp.database.DatabaseFactory
import com.tunaateskoc.rickandmortyapp.securepreferences.AndroidSecurePreferencesRepository
import com.tunaateskoc.rickandmortyapp.securepreferences.EncryptedDataStoreHelper.createEncryptedPrefs
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<HttpClientEngine> { OkHttp.create() }

    single<SharedPreferences> {
        createEncryptedPrefs(androidContext())
    }
    single<SecurePreferencesRepository> {
        AndroidSecurePreferencesRepository(get())
    }

    single { DatabaseFactory(androidApplication()) }
}