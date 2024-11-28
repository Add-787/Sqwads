package com.psyluckco.sqwads.feature.home

import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import kotlinx.serialization.Serializable

@Serializable
data object Home

fun NavController.navigateToHome(navOptions: NavOptions? = navOptions {
    popUpTo(0) { inclusive = true }
    launchSingleTop = true
}) {
    navigate(Home, navOptions)
}

fun NavGraphBuilder.homeScreen(
    navigateToSquad: (String) -> Unit,
    navigateToProfile: () -> Unit
) {
    composable<Home> {
        HomeRoute(
            navigateToSquad = navigateToSquad,
            navigateToProfile = navigateToProfile
        )
    }
}
