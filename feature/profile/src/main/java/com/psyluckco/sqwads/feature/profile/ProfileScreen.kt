package com.psyluckco.sqwads.feature.profile

import android.graphics.drawable.Icon
import androidx.annotation.StringRes
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.psyluckco.sqwads.core.design.component.AppWrapper
import com.psyluckco.sqwads.core.design.component.DefaultTextButton
import com.psyluckco.sqwads.core.design.component.SqwadsProgressLoadingDialog
import com.psyluckco.sqwads.core.design.component.SqwadsTopAppBar
import com.psyluckco.sqwads.core.design.theme.SqwadsTheme
import com.psyluckco.sqwads.core.model.LoadingState

@Composable
internal  fun ProfileRoute(
    popup: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navigationState by viewModel.navigationState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = navigationState) {
        fun performNavigation(action: () -> Unit) {
            action()
            viewModel.resetNavigation()
        }
        with(navigationState) {
            when(this) {
                is NavigationState.NavigateToHome -> performNavigation { popup() }
                is NavigationState.None -> Unit
            }
        }
    }

    val onEvent by rememberUpdatedState(
        newValue = { event: ProfileEvent ->
            viewModel.onEvent(event)
        }
    )

    if(uiState.loadingState is LoadingState.Loading) {
        SqwadsProgressLoadingDialog(id = com.psyluckco.sqwads.core.design.R.string.placeholder)
    }

    ProfileScreen(
        uiState = uiState,
        onEvent= onEvent
    )
}

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onEvent: (ProfileEvent) -> Unit,
   modifier: Modifier = Modifier
){
    Column {
        SqwadsTopAppBar(
            R.string.profile_page_header,
            showNavigationIcon = true,
            onBackClicked = {
                onEvent(ProfileEvent.OnBackButtonClicked)
            }
        )
        AppWrapper(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
            ProfileImage()

            Column (
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1F)
                    .padding(vertical = 30.dp)
            ){
                ProfileDetailRow(
                    textId = R.string.user_name_label,
                    icon = Icons.Default.Person,
                    value = uiState.user.name
                )
                Spacer(modifier = Modifier.height(30.dp))
                ProfileDetailRow(
                    textId = R.string.user_email_label,
                    icon = Icons.Default.Email,
                    value = uiState.user.email
                )
            }

            DefaultTextButton(
                text = R.string.logout_btn_label,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                onEvent(ProfileEvent.OnLogoutClicked)
            }
        }
    }
}

@Composable
fun ProfileImage(){
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(75.dp)
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
    }
}

@Composable
fun ProfileDetailRow(
    @StringRes textId: Int,
    value: String,
    icon: ImageVector,
){

    Row{
        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(30.dp))
        Spacer(modifier = Modifier.width(20.dp))
        Column {
            Text(stringResource(id = textId))
            Spacer(modifier = Modifier.height(10.dp))
            Text(value, fontSize = 20.sp)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ProfileScreenPreview(){
    SqwadsTheme {
        ProfileScreen(
            uiState = ProfileUiState(),
            onEvent = {}
        )
    }
}