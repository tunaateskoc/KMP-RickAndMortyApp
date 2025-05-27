package com.tunaateskoc.rickandmortyapp

import androidx.compose.runtime.Composable
import com.tunaateskoc.rickandmortyapp.navigation.Navigation
import com.tunaateskoc.rickandmortyapp.theme.RickAndMortyAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    RickAndMortyAppTheme {
        Navigation()
    }
}