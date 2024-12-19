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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import java.time.format.DateTimeFormatter
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
            displayName = uiState.userName,
            onEvent = onEvent,
        )

        CreateNewRoomCard(
            creatingNewRoom = {
                onEvent(HomeEvent.OnEditRoomNameDialogOpened)
            }
        )

        Spacer(modifier = Modifier.height(5.dp))

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
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onBackground,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(10.dp))
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
            .height(120.dp)
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
                Text(text = stringResource(id = AppText.create_room_button_text))
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
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn (
            modifier = Modifier.fillMaxWidth(),
        ){
            items(rooms){
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
    Column(modifier = Modifier.clickable { navigateToRoom(room.name) }){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxSize()

        ) {
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .clip(RoundedCornerShape(size = 10.dp))
                    .background(MaterialTheme.colorScheme.onSurfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp),
                    tint = MaterialTheme.colorScheme.background,

                )
            }

            Spacer(modifier = modifier.width(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(text = room.name, fontSize = 18.sp)
                Text(
                    text = DateTimeFormatter.ofPattern("dd MMM yy, hh:MM a").format(room.createdAt),
                    style = MaterialTheme.typography.labelSmall
                )

            }
        }
        HorizontalDivider(
            color = MaterialTheme.colorScheme.surfaceContainerLow
        )
    }
}
