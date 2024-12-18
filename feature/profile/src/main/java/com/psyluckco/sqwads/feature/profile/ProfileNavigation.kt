package com.psyluckco.sqwads.feature.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object Profile

fun NavController.navigateToProfile(
    navOptions: NavOptions? = null
){
    navigate(Profile, navOptions)
}

fun NavGraphBuilder.profileScreen(
    popup: () -> Unit
){
    composable<Profile> {
        ProfileRoute(popup = popup)
    }
}

