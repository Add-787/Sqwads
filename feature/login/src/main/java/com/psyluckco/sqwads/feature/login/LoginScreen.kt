package com.psyluckco.sqwads.feature.login

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.psyluckco.sqwads.core.design.component.AppWrapper
import com.psyluckco.sqwads.core.design.component.DefaultPasswordField
import com.psyluckco.sqwads.core.design.component.DefaultTextButton
import com.psyluckco.sqwads.core.design.component.DefaultTextField
import com.psyluckco.sqwads.core.design.component.HeaderWrapper
import com.psyluckco.sqwads.core.design.component.SqwadsProgressLoadingDialog
import com.psyluckco.sqwads.core.design.theme.SqwadsTheme
import com.psyluckco.sqwads.core.model.LoadingState
import com.psyluckco.sqwads.core.design.R.string as AppText

@Composable
internal fun LoginRoute(
    navigateToHome: (String) -> Unit,
    navigateToRegister: () -> Unit,
    navigateToForgotPassword: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//    val onEvent by rememberUpdatedState(
//        newValue = { event : LoginEvent -> viewModel.onEvent(event)}
//    )

    val navigationState by viewModel.navigationState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = navigationState) {
        fun performNavigation(action: () -> Unit) {
            action()
            viewModel.resetNavigation()
        }
        with(navigationState) {
            when(this) {
                is NavigationState.NavigateToHome -> performNavigation { navigateToHome(this.userId) }
                is NavigationState.NavigateToRegister -> performNavigation(navigateToRegister)
                is NavigationState.NavigateToForgotPassword -> performNavigation(navigateToForgotPassword)
                is NavigationState.None -> Unit
            }
        }
    }

    if(uiState.loadingState is LoadingState.Loading) SqwadsProgressLoadingDialog(id = AppText.placeholder)

    LoginScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
    
}

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onEvent: (LoginEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    AppWrapper(modifier.background(color = MaterialTheme.colorScheme.background)) {
        LoginHeader()

        Column(
            modifier = modifier.fillMaxSize(),
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                DefaultTextField(
                    value = uiState.email,
                    label = AppText.placeholder,
                    leadingIcon = Icons.Default.Email,
                    onValueChange = { onEvent(LoginEvent.OnEmailChanged(it)) }
                )

                Spacer(modifier = Modifier.height(30.dp))

                DefaultPasswordField(
                    value = uiState.password,
                    label = AppText.placeholder,
                    leadingIcon = Icons.Default.Lock,
                    onValueChange = { onEvent(LoginEvent.OnPasswordChanged(it)) }
                )

                Spacer(modifier = Modifier.height(75.dp))

                DefaultTextButton(text = AppText.login_button) {
                    onEvent(LoginEvent.OnLoginClicked)
                }

                Spacer(modifier = Modifier.height(30.dp))

                Text(text = stringResource(id = AppText.placeholder))

                Spacer(modifier = Modifier.height(30.dp))

                DefaultTextButton(text = AppText.register_button) {
                    onEvent(LoginEvent.OnRegisterClicked)

                }
                
            }

        }
        
    }
}

@Composable
fun LoginHeader(modifier: Modifier = Modifier) {
    HeaderWrapper(modifier = modifier
        .fillMaxWidth()
    ) {
        Text(
            text = stringResource(AppText.login_header),
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = stringResource(AppText.login_sub_header),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            minLines = 1,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    SqwadsTheme {
        LoginScreen(uiState = LoginUiState(email = "testuser@email.com"), {})
    }

}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginScreenDarkPreview() {
    SqwadsTheme {
        LoginScreen(uiState = LoginUiState(email = "testuser@email.com", password = "admin"), {})
    }
} 
