package com.psyluckco.sqwads.feature.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import kotlinx.serialization.Serializable

@Serializable
data class Home(val userId: String)

fun NavController.navigateToHome(userId: String ,navOptions: NavOptions? = navOptions {
    popUpTo(0) { inclusive = true }
    launchSingleTop = true
}) {
    navigate(Home(userId), navOptions)
}

fun NavGraphBuilder.homeScreen(
    navigateToSquad: (String) -> Unit,
    navigateToProfile: () -> Unit
) {
    composable<Home> {
        // Using savedStateHandle in VM can be better
        val userId = it.arguments?.getString("userId")
        HomeRoute(userId = userId ?: "",navigateToSquad = navigateToSquad, navigateToProfile = navigateToProfile)
    }
}
