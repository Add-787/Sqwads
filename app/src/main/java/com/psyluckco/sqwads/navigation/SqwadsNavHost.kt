/**
 * Created by developer on 07-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.psyluckco.register.navigateToRegister
import com.psyluckco.register.registerScreen
import com.psyluckco.sqwads.SqwadsAppState
import com.psyluckco.sqwads.feature.home.homeScreen
import com.psyluckco.sqwads.feature.home.navigateToHome
import com.psyluckco.sqwads.feature.login.Login
import com.psyluckco.sqwads.feature.login.loginScreen
import com.psyluckco.sqwads.feature.login.navigateToLogin

@Composable
fun SqwadsNavHost(
    appState: SqwadsAppState,
    modifier: Modifier = Modifier,
    startDestination: Any = Login
) {

    val navHostController = appState.navHostController

    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startDestination
    ) {

        loginScreen(
            navigateToHome = navHostController::navigateToHome,
            navigateToRegister = navHostController::navigateToRegister,
            navigateToForgotPassword = { }
        )

        homeScreen(
            navigateToProfile = { },
            navigateToSquad = { }
        )

        registerScreen(
            navigateToLogin = navHostController::navigateToLogin,
            navigateToHome = { }
        )
    }

}