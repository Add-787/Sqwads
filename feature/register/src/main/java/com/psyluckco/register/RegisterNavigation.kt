/**
 * Created by developer on 08-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.register

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object Register

fun NavController.navigateToRegister(navOptions: NavOptions? = null) {
    navigate(Register, navOptions)
}

fun NavGraphBuilder.registerScreen(
    navigateToHome: (String) -> Unit,
    navigateToLogin: () -> Unit
) {

    composable<Register> {
        RegisterRoute(
            navigateToHome = navigateToHome,
            navigateToLogin = navigateToLogin
        )
    }

}