package com.psyluckco.sqwads.feature.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import com.psyluckco.sqwads.core.design.component.DefaultEditRoomDialog
import com.psyluckco.sqwads.core.design.component.HeaderWrapper
import com.psyluckco.sqwads.core.design.component.SqwadsProgressLoadingDialog
import com.psyluckco.sqwads.core.design.theme.SqwadsTheme
import com.psyluckco.sqwads.core.model.LoadingState
import com.psyluckco.sqwads.core.model.Room
import java.time.LocalDateTime
import com.psyluckco.sqwads.core.design.R.string as AppText

@Composable
internal fun HomeRoute(
    userId: String,
    navigateToRoom: (String) -> Unit,
    navigateToProfile: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.initialize()
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val navigationState by viewModel.navigationState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = navigationState) {
        fun performNavigation(action: () -> Unit) {
            action()
            viewModel.resetNavigation()
        }

        with(navigationState) {
            when(this) {
                NavigationState.NavigateToProfile -> performNavigation { navigateToProfile() }
                is NavigationState.NavigateToRoom -> performNavigation { navigateToRoom(this.roomId) }
                NavigationState.None -> Unit
            }
        }

    }

    /**
     * Uses [rememberUpdatedState] for `onEvent` to prevent unnecessary recompositions by maintaining
     * a stable reference. This method ensures minimal performance impact and efficient UI updates by
     * allowing `onEvent` to adapt dynamically to state changes without causing full screen
     * recompositions, thus optimizing UI responsiveness and performance.
     */
    val onEvent by rememberUpdatedState(
        newValue = { event: HomeEvent ->
            viewModel.onEvent(event)
        }
    )

    if(uiState.loadingState is LoadingState.Loading) {
        SqwadsProgressLoadingDialog(id = com.psyluckco.sqwads.core.design.R.string.placeholder)
    }

    HomeScreen(
        uiState = uiState,
        onEvent = onEvent,
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    AppWrapper(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
        HomeHeader(
            modifier = modifier,
            onEvent = onEvent,
        )

        CreateNewRoomCard(
            creatingNewRoom = {
                onEvent(HomeEvent.OnEditRoomNameDialogOpened)
            }
        )

        Spacer(modifier = Modifier.height(9.dp))

        JoinRoomsSection(
            rooms = uiState.rooms,
            onEvent = onEvent
        )

    }

    if(uiState.isDialogOpened) {
        DefaultEditRoomDialog(
            title = AppText.edit_room_dialog_header,
            onConfirm = { onEvent(HomeEvent.OnRoomNameProvided(it)) },
            onDismiss = { onEvent(HomeEvent.OnEditRoomNameDialogClosed) }
        )
    }
}

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    displayName: String = "Guest",
    onEvent: (HomeEvent) -> Unit,
) {
    HeaderWrapper(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .height(95.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Hi $displayName!",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onBackground)
                    .clickable {
                        onEvent(HomeEvent.OnProfileClicked)
                    },
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

        }
    }

}



@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {

    val fakeRooms = listOf(
        Room(
            id = "1",
            name = "test_room_1",
            members = listOf("user1","user2"),
            createdAt = LocalDateTime.now(),
            createdBy = "user1"
        ),
        Room(
            id = "2",
            name = "test_room_2",
            members = listOf("user3","user4","user5"),
            createdAt = LocalDateTime.now(),
            createdBy = "user3"
        ),
        Room(
            id = "3",
            name = "test_room_3",
            members = listOf("user6","user7"),
            createdAt = LocalDateTime.now(),
            createdBy = "user6"
        )
    )

    SqwadsTheme {
        HomeScreen(
            uiState = HomeUiState(
                loadingState = LoadingState.Idle,
                rooms = fakeRooms
            ),
            onEvent = {},
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeScreenDarkPreview() {

    val fakeRooms = listOf(
        Room(
            id = "1",
            name = "test_room_1",
            members = listOf("user1","user2"),
            createdAt = LocalDateTime.now(),
            createdBy = "user1"
        ),
        Room(
            id = "2",
            name = "test_room_2",
            members = listOf("user3","user4","user5"),
            createdAt = LocalDateTime.now(),
            createdBy = "user3"
        ),
        Room(
            id = "3",
            name = "test_room_3",
            members = listOf("user6","user7"),
            createdAt = LocalDateTime.now(),
            createdBy = "user6"
        )
    )

    SqwadsTheme {
        HomeScreen(
            uiState = HomeUiState(
                loadingState = LoadingState.Idle,
                rooms = fakeRooms
            ),
            onEvent = { },
        )
    }
}

@Composable
private fun CreateNewRoomCard(
    modifier: Modifier = Modifier,
    creatingNewRoom: () -> Unit,
) {
    Card(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
            .clickable { creatingNewRoom() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 28.dp
        )
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(11.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text(text = stringResource(id = AppText.placeholder_section_text))
            }

            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = null,
                modifier = Modifier.size(90.dp)
            )
        }

    }
}

@Composable
fun JoinRoomsSection(
    rooms: List<Room>,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {

        Text(
            text = stringResource(id = AppText.join_rooms_section_header),
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(rooms) {
                room -> RoomCard(
                    room = room,
                    navigateToRoom = {
                        onEvent(HomeEvent.OnRoomJoining(room.id))
                    }
                )

            }
            
        }

    }
}

@Composable
fun RoomCard(
    room: Room,
    modifier: Modifier = Modifier,
    navigateToRoom: (String) -> Unit
) {

    Card(
        modifier = Modifier
            .size(90.dp)
            .padding(all = 4.dp)
            .clickable { navigateToRoom(room.name) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 7.dp
        )
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(5.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = room.name)
                Text(
                    text = room.createdAt.dayOfWeek.toString(),
                    style = MaterialTheme.typography.labelSmall
                )

            }
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = null,
                modifier = Modifier.size(55.dp)
            )

        }


    }


}
