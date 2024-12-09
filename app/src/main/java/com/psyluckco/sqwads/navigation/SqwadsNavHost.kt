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
import com.psyluckco.forgot_password.forgotPasswordScreen
import com.psyluckco.forgot_password.navigateToForgotPassword
import com.psyluckco.register.navigateToRegister
import com.psyluckco.register.registerScreen
import com.psyluckco.sqwads.SqwadsAppState
import com.psyluckco.sqwads.feature.home.homeScreen
import com.psyluckco.sqwads.feature.home.navigateToHome
import com.psyluckco.sqwads.feature.joined_room.joinedRoomScreen
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
            navigateToForgotPassword = navHostController::navigateToForgotPassword
        )

        homeScreen(
            navigateToProfile = { },
            navigateToSquad = { }
        )

        joinedRoomScreen(
            popup = navHostController::popBackStack,
            navigateToGame = { }
        )

        registerScreen(
            navigateToLogin = navHostController::navigateToLogin,
            navigateToHome = navHostController::navigateToHome
        )

        forgotPasswordScreen(navigateToLogin = navHostController::navigateToLogin)

    }

}