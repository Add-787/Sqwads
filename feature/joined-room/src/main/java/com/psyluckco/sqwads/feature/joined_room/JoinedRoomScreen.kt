/**
 * Created by developer on 06-12-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.feature.joined_room

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.psyluckco.sqwads.core.design.component.AppWrapper
import com.psyluckco.sqwads.core.design.component.DefaultTextButton
import com.psyluckco.sqwads.core.design.component.HeaderWrapper
import com.psyluckco.sqwads.core.design.component.SqwadsProgressLoadingDialog
import com.psyluckco.sqwads.core.design.theme.SqwadsTheme
import com.psyluckco.sqwads.core.model.LoadingState
import com.psyluckco.sqwads.core.design.R.string as AppText

@Composable
internal fun JoinedRoomRoute(
    id: String,
    popUp: () -> Unit,
    navigateToGame: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: JoinedRoomViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    LaunchedEffect(key1 = id) {


    }

    /**
     * Uses [rememberUpdatedState] for `onEvent` to prevent unnecessary recompositions by maintaining
     * a stable reference. This method ensures minimal performance impact and efficient UI updates by
     * allowing `onEvent` to adapt dynamically to state changes without causing full screen
     * recompositions, thus optimizing UI responsiveness and performance.
     */
    val onEvent by rememberUpdatedState(
        newValue = { event: JoinedRoomEvent ->
            viewModel.onEvent(event)
        }
    )

    if(uiState.loadingState is LoadingState.Loading) {
        SqwadsProgressLoadingDialog(id = AppText.placeholder)
    }

    JoinedRoomScreen(
        uiState = uiState,
        popUp = popUp,
        onEvent = onEvent
    )


}

@Composable
fun JoinedRoomScreen(
    uiState: JoinedRoomUiState,
    popUp: () -> Unit,
    onEvent: (JoinedRoomEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AppWrapper(
        modifier = Modifier.background(
            MaterialTheme.colorScheme.background
        )
    ) {

        JoinedRoomHeader(
            popUp = popUp,
            name = uiState.roomName
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxSize()
        ) {

            JoinedMembersCard(
                members = uiState.members
            )

            GameInfoCard()

            DefaultTextButton(
                text = AppText.leave_room_button,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {

            }

        }

    }
}

@Composable
fun JoinedMembersCard(
    members: List<String> = emptyList()
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 28.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 9.dp)
        ) {
            Text(
                text = stringResource(id = AppText.joined_members_card_header),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            LazyHorizontalGrid(
                rows = GridCells.Fixed(1),
            ) {
                items(members) {
                    MemberCard(
                        member = it
                    )

                }
                
            }

        }

    }
    
}

@Composable
fun GameInfoCard(modifier: Modifier = Modifier) {

    Card(
        modifier = Modifier
            .fillMaxSize(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 28.dp
        )
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(9.dp)
        ) {
            Text(
                text = stringResource(id = AppText.placeholder),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )


        }

    }

}

@Composable
fun MemberCard(
    modifier: Modifier = Modifier,
    member: String = "",
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .size(65.dp)
            .padding(all = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onBackground),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                tint = MaterialTheme.colorScheme.background
            )
        }

        Text(text = member)
    }
}

@Composable
fun JoinedRoomHeader(
    popUp: () -> Unit,
    name: String
) {
    HeaderWrapper(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = popUp) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(60.dp),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.width(11.dp))

            Text(
                text = name,
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun JoinedRoomScreenPreview() {

    val fakeMembers = listOf("user1","user2","user3")
    SqwadsTheme {
        JoinedRoomScreen(
            popUp = { },
            onEvent = { },
            uiState = JoinedRoomUiState(
                roomName = "test_room",
                members = fakeMembers,
            )
        )
    }
    
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun JoinedRoomScreenDarkPreview() {
    val fakeMembers = listOf("user1", "user2", "user3")

    SqwadsTheme {
        JoinedRoomScreen(
            popUp = { },
            onEvent = { },
            uiState = JoinedRoomUiState
                (
                roomName = "test_room",
                members = fakeMembers
            )
        )
    }

}