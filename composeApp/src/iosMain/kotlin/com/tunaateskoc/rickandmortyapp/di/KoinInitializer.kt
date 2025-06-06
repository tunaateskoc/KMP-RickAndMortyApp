package com.tunaateskoc.rickandmortyapp.di

import org.koin.core.context.startKoin

actual class KoinInitializer {
    actual fun init() {
        startKoin {
            modules(sharedModule, platformModule)
        }
    }
}