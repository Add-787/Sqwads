package com.psyluckco.forgot_password

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.psyluckco.forgot_password.ForgotPassword
import kotlinx.serialization.Serializable

@Serializable
data object ForgotPassword

fun NavController.navigateToForgotPassword(navOptions: NavOptions? = null) {
    navigate(ForgotPassword, navOptions)
}

fun NavGraphBuilder.forgotPasswordScreen(
    navigateToLogin: () -> Unit
) {
    composable<ForgotPassword> {
        ForgotPasswordRoute(clearAndNavigateLogin = navigateToLogin)
    }
}