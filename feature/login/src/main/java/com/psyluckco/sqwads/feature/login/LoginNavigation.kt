package com.psyluckco.sqwads.feature.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object Login

fun NavController.navigateToLogin(
    navOptionsBuilder: NavOptionsBuilder.() -> Unit = {
        launchSingleTop = true
        popUpTo(0) { inclusive = true }
    }
) {
    navigate(Login, builder = navOptionsBuilder)
}

fun NavGraphBuilder.loginScreen(
    navigateToHome: (String) -> Unit,
    navigateToRegister: () -> Unit,
    navigateToForgotPassword: () -> Unit
) {
    composable<Login> {
        LoginRoute(
            navigateToHome,
            navigateToRegister,
            navigateToForgotPassword
        )
    }
}
