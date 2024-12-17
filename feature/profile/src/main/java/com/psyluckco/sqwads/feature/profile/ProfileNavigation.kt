package com.psyluckco.sqwads.feature.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import kotlinx.serialization.Serializable

@Serializable
data object Profile

fun NavController.navigateToProfile(

    navOptions: NavOptions? = navOptions{
        popUpTo(0) {
            inclusive = true
        }
        launchSingleTop = true
    }
){
    navigate(Profile, navOptions)
}

fun NavGraphBuilder.profileScreen(){
    composable<Profile> {
        ProfileRoute()
    }
}

