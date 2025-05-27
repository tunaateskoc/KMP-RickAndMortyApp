package com.tunaateskoc.rickandmortyapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tunaateskoc.rickandmortyapp.feature.screen.detail.CharacterDetailScreen
import com.tunaateskoc.rickandmortyapp.feature.screen.list.CharacterListScreen
import org.koin.compose.viewmodel.koinViewModel

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

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}