/**
 * Created by developer on 07-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.psyluckco.forgot_password.forgotPasswordScreen
import com.psyluckco.forgot_password.navigateToForgotPassword
import com.psyluckco.register.navigateToRegister
import com.psyluckco.register.registerScreen
import com.psyluckco.sqwads.SqwadsAppState
import com.psyluckco.sqwads.feature.home.homeScreen
import com.psyluckco.sqwads.feature.home.navigateToHome
import com.psyluckco.sqwads.feature.joined_room.joinedRoomScreen
import com.psyluckco.sqwads.feature.joined_room.navigateToJoinedRoom
import com.psyluckco.sqwads.feature.login.Login
import com.psyluckco.sqwads.feature.login.loginScreen
import com.psyluckco.sqwads.feature.login.navigateToLogin
import com.psyluckco.sqwads.feature.profile.navigateToProfile
import com.psyluckco.sqwads.feature.profile.profileScreen
import com.psyluckco.sqwads.feature.stats.navigateToStats
import com.psyluckco.sqwads.feature.stats.statsScreen

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
            navigateToProfile = navHostController::navigateToProfile,
            navigateToRoom = navHostController::navigateToJoinedRoom,
            navigateToStats = navHostController::navigateToStats
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

        profileScreen(
            popup = navHostController::popBackStack,
        )

        statsScreen(
            popup = navHostController::popBackStack
        )

    }

}