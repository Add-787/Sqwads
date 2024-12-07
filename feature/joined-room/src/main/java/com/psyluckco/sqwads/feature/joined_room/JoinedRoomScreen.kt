/**
 * Created by developer on 06-12-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.feature.joined_room

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.psyluckco.sqwads.core.design.component.AppWrapper
import com.psyluckco.sqwads.core.design.component.HeaderWrapper
import com.psyluckco.sqwads.core.design.theme.SqwadsTheme

@Composable
internal fun JoinedRoomRoute(
    popUp: () -> Unit,
    navigateToGame: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: JoinedRoomViewModel = hiltViewModel()
) {

    /**
     * Uses [rememberUpdatedState] for `onEvent` to prevent unnecessary recompositions by maintaining
     * a stable reference. This method ensures minimal performance impact and efficient UI updates by
     * allowing `onEvent` to adapt dynamically to state changes without causing full screen
     * recompositions, thus optimizing UI responsiveness and performance.
     */
//    val onEvent by rememberUpdatedState(
//        newValue = { event: JoinedRoomUiEvent ->
//            viewModel.onEvent(event)
//        }
//    )





}

@Composable
fun JoinedRoomScreen(
    uiState: JoinedRoomUiState
) {
    AppWrapper(
        modifier = Modifier.background(
            MaterialTheme.colorScheme.background
        )
    ) {

        JoinedRoomHeader(name = uiState.roomName)

    }
}

@Composable
fun JoinedRoomHeader(
    name: String
) {
    HeaderWrapper(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    modifier = Modifier.size(60.dp),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.width(11.dp))

            Text(
                text = name,
                style = MaterialTheme.typography.displayMedium
            )
            
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun JoinedRoomScreenPreview() {
    SqwadsTheme {
        JoinedRoomScreen(uiState = JoinedRoomUiState(roomName = "test_room"))
    }
    
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun JoinedRoomScreenDarkPreview() {
    SqwadsTheme {
        JoinedRoomScreen(uiState = JoinedRoomUiState(roomName = "test_room"))
    }

}