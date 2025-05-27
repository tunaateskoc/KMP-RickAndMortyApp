package com.tunaateskoc.rickandmortyapp.di

import android.app.Application

class RickAndMortyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        KoinInitializer(applicationContext).init()
    }
}