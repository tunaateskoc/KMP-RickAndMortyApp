package com.tunaateskoc.rickandmortyapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tunaateskoc.rickandmortyapp.feature.screen.detail.CharacterDetailScreen
import com.tunaateskoc.rickandmortyapp.feature.screen.list.CharacterListScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.CharacterListScreen) {
        composable<Screen.CharacterListScreen> {
            CharacterListScreen(navController)
        }
        composable<Screen.CharacterDetailScreen> {
            CharacterDetailScreen(navController)
        }
    }
}