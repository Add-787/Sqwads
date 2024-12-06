/**
 * Created by developer on 06-12-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.feature.joined_room

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object JoinedRoom

fun NavController.navigateToJoinedRoom(navOptions: NavOptions) {
    navigate(JoinedRoom, navOptions)
}

fun NavGraphBuilder.joinedRoomScreen(
    popup: () -> Unit,
    navigateToGame: () -> Unit
) {

    composable<JoinedRoom> {
        JoinedRoomRoute(popUp = popup, navigateToGame = navigateToGame)
    }

}