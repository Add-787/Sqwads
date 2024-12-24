/**
 * Created by developer on 06-12-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.feature.joined_room

import android.content.res.Configuration
import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.psyluckco.sqwads.core.design.IconType
import com.psyluckco.sqwads.core.design.component.AppWrapper
import com.psyluckco.sqwads.core.design.component.DefaultActionButton
import com.psyluckco.sqwads.core.design.component.DefaultTextField
import com.psyluckco.sqwads.core.design.component.HeaderWrapper
import com.psyluckco.sqwads.core.design.component.SqwadsProgressLoadingDialog
import com.psyluckco.sqwads.core.design.theme.SqwadsTheme
import com.psyluckco.sqwads.core.model.LoadingState
import com.psyluckco.sqwads.core.model.Message
import java.time.format.DateTimeFormatter
import com.psyluckco.sqwads.core.design.R.string as AppText

@Composable
internal fun JoinedRoomRoute(
    roomId: String,
    popUp: () -> Unit,
    navigateToGame: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: JoinedRoomViewModel = hiltViewModel()
) {

    val navigationState by viewModel.navigationState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = navigationState) {
        viewModel.initialize(roomId)
        fun performNavigation(action: () -> Unit) {
            action()
            viewModel.resetNavigation()
        }

        with(navigationState){
            when(this){
                is NavigationState.NavigateToHome -> { performNavigation { popUp() } }
                else -> Unit
            }
        }

    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val message by viewModel.message.collectAsStateWithLifecycle()


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

    JoinedRoomScreen(
        roomId = roomId,
        uiState = uiState,
        popUp = popUp,
        message = message,
        onMessageChange = viewModel::onMessageChange,
        onEvent = onEvent
    )

    if(uiState.loadingState is LoadingState.Loading) {
        Box(modifier = Modifier.fillMaxSize()) {
            SqwadsProgressLoadingDialog(id = AppText.placeholder)
        }
    }


}

@Composable
fun JoinedRoomScreen(
    roomId: String,
    message: String = "",
    onMessageChange: (String) -> Unit,
    uiState: JoinedRoomUiState,
    popUp: () -> Unit,
    onEvent: (JoinedRoomEvent) -> Unit,
) {
    AppWrapper(
        modifier = Modifier.background(
            MaterialTheme.colorScheme.background
        )
    ) {

        JoinedRoomHeader(
            roomId = roomId,
            onEvent = onEvent,
            name = uiState.roomName
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxSize()
        ) {

            JoinedMembersCard(
                modifier = Modifier,
                members = uiState.members
            )

            Spacer(modifier = Modifier.height(30.dp))

            ConversationCard(
                modifier = Modifier.fillMaxSize(),
                roomId = roomId,
                messages = uiState.messages,
                sendingMessage = message,
                onMessageChange = onMessageChange,
                onEvent = onEvent
            )

        }

    }
}

@Composable
fun JoinedMembersCard(
    modifier: Modifier = Modifier,
    members: List<String> = emptyList()
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
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
                .wrapContentHeight()
                .padding(all = 9.dp)
        ) {
            Text(
                text = stringResource(id = AppText.joined_members_card_header),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            LazyHorizontalGrid(
                rows = GridCells.Fixed(1),
                modifier = Modifier.height(80.dp)
            ) {
                items(members) {
                    MemberCard(
                        memberName = it
                    )

                }
                
            }

        }

    }
    
}

//@Composable
//fun GameInfoCard(modifier: Modifier = Modifier) {
//
//    Card(
//        modifier = Modifier
//            .fillMaxSize(),
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.secondaryContainer
//        ),
//        elevation = CardDefaults.cardElevation(
//            defaultElevation = 28.dp
//        )
//    ) {
//        Column(
//            modifier = modifier
//                .fillMaxWidth()
//                .padding(9.dp)
//        ) {
//            Text(
//                text = stringResource(id = AppText.placeholder),
//                style = MaterialTheme.typography.titleLarge,
//                color = MaterialTheme.colorScheme.onBackground
//            )
//        }
//
//    }
//
//}

@Composable
fun ConversationCard(
    modifier: Modifier = Modifier,
    roomId: String = "",
    messages: List<Message>,
    sendingMessage: String = "",
    onMessageChange: (String) -> Unit,
    onEvent: (JoinedRoomEvent) -> Unit
) {
    Card(
        modifier = modifier,
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
                .padding(9.dp),
        ) {

            MessagesContent(
                messages = messages
            )

            Row(
                modifier = Modifier
                    .height(60.dp)
                    .imePadding(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DefaultTextField(
                    modifier = Modifier
                        .width(intrinsicSize = IntrinsicSize.Min),
                    value = sendingMessage,
                    label = AppText.placeholder,
                    leadingIcon = Icons.Default.MailOutline,
                    onValueChange = onMessageChange
                )

                Spacer(modifier = Modifier.width(12.dp))

                IconButton(
                    onClick = {
                        if(sendingMessage.isNotBlank()){
                            onEvent(JoinedRoomEvent.OnMessageSent(roomId = roomId, message = sendingMessage))
                        }
                              },
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(9.dp))
                        .background(color = MaterialTheme.colorScheme.primary)
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(9.dp),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    ,
                ) {
                    Icon(imageVector = Icons.Default.Send, contentDescription = null)
                }
            }
        }

    }

}

@Composable
fun MessagesContent(
    modifier: Modifier = Modifier,
    messages: List<Message>
) {
    Box(modifier = modifier.fillMaxHeight(0.8f)) {
        LazyColumn {
            val sortedMessages = messages.sortedBy { it.sentAt }
            items(sortedMessages) {

                if(it.fromCurrentUser) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        ChatBubble(message = it)
                        Spacer(modifier = Modifier.width(4.dp))
                        MemberCard(
                            memberName = it.sentBy
                        )
                    }


                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        MemberCard(
                            memberName = it.sentBy
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        ChatBubble(message = it)
                    }
                }
            }

        }

    }

}

@Composable
fun ChatBubble(
    modifier: Modifier = Modifier,
    message: Message
) {
    Column(
        modifier = Modifier.width(200.dp),
        horizontalAlignment = Alignment.End
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = if(!message.fromCurrentUser) CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            ) else CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground)
        ) {
            Box(modifier = Modifier.padding(8.dp)) {
                Text(text = message.text, style = MaterialTheme.typography.labelLarge)
            }
        }

        if(message.sentAt != null) {
            Text(
                text = DateTimeFormatter.ofPattern("hh:mm a").format(message.sentAt),
                style = MaterialTheme.typography.labelSmall
            )
        }


    }

}

@Composable
fun MemberCard(
    modifier: Modifier = Modifier,
    memberName: String = "",
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
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

        Text(text = memberName, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun JoinedRoomHeader(
    roomId: String,
    onEvent: (JoinedRoomEvent) -> Unit,
    name: String
) {
    HeaderWrapper(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = {
                onEvent(JoinedRoomEvent.LeaveRoomClicked(roomId))
            }) {
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

    val fakeMessages = listOf(
        Message(
            text = "Hello",
            sentBy = "user1"
        ),
        Message(
            text = "Hii",
            sentBy = "user2",
            fromCurrentUser = true
        )
    )


    SqwadsTheme {
        JoinedRoomScreen(
            roomId = "",
            popUp = { },
            onEvent = { },
            onMessageChange = { },
            uiState = JoinedRoomUiState(
                roomName = "test_room",
                messages = fakeMessages,
                members = fakeMembers,
            )
        )
    }
    
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun JoinedRoomScreenDarkPreview() {
    val fakeMembers = listOf("user1", "user2", "user3")
    val fakeMessages = listOf(
        Message(
            text = "Hello",
            sentBy = "user1"
        ),
        Message(
            text = "Hii",
            sentBy = "user2",
            fromCurrentUser = true
        )
    )

    SqwadsTheme {
        JoinedRoomScreen(
            roomId = "",
            popUp = { },
            onEvent = { },
            onMessageChange = { },
            uiState = JoinedRoomUiState
                (
                roomName = "test_room",
                messages = fakeMessages,
                members = fakeMembers
            )
        )
    }

}