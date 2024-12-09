package com.psyluckco.sqwads.feature.home

import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class Home(
    val userName: String
)

fun NavController.navigateToHome(navOptions: NavOptions? = navOptions {
    popUpTo(0) { inclusive = true }
    launchSingleTop = true
}) {
    navigate(Home, navOptions)
}

fun NavGraphBuilder.homeScreen(
    navigateToRoom: (String) -> Unit,
    navigateToProfile: () -> Unit
) {
    composable<Home> {

        backStackEntry ->
            val home: Home = backStackEntry.toRoute()

        HomeRoute(
            userName = home.userName,
            navigateToRoom = navigateToRoom,
            navigateToProfile = navigateToProfile
        )
    }
}
