package com.tunaateskoc.rickandmortyapp

import androidx.compose.ui.window.ComposeUIViewController
import com.tunaateskoc.rickandmortyapp.di.KoinInitializer

fun MainViewController() = ComposeUIViewController(
    configure = {
        KoinInitializer().init()
    }
) {
    App()
}