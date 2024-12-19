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
    val id: String
)

fun NavController.navigateToHome(
    userId: String,
    navOptions: NavOptions? = navOptions {
    popUpTo(0) { inclusive = true }
    launchSingleTop = true
}) {
    navigate(Home(userId), navOptions)
}

fun NavGraphBuilder.homeScreen(
    navigateToRoom: (String) -> Unit,
    navigateToProfile: () -> Unit
) {

    composable<Home> {
        backStackEntry ->
        val homePage = backStackEntry.toRoute<Home>()
        HomeRoute(
            userId = homePage.id,
            navigateToRoom = navigateToRoom,
            navigateToProfile = navigateToProfile
        )
    }
}
