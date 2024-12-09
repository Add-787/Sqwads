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
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class JoinedRoom(
    val roomId : String
)

fun NavController.navigateToJoinedRoom(
    roomId: String,
    navOptions: NavOptions
) {
    navigate(JoinedRoom(roomId), navOptions)
}

fun NavGraphBuilder.joinedRoomScreen(
    popup: () -> Unit,
    navigateToGame: () -> Unit
) {

    composable<JoinedRoom> {
        backStackEntry ->

        val joinedRoom : JoinedRoom = backStackEntry.toRoute()

        JoinedRoomRoute(
            id = joinedRoom.roomId,
            popUp = popup,
            navigateToGame = navigateToGame
        )
    }

}