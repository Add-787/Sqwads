/**
 * Created by developer on 18-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.psyluckco.sqwads.core.design.component.AppWrapper
import com.psyluckco.sqwads.core.design.component.DefaultPasswordField
import com.psyluckco.sqwads.core.design.component.DefaultTextButton
import com.psyluckco.sqwads.core.design.component.DefaultTextField
import com.psyluckco.sqwads.core.design.component.HeaderWrapper
import com.psyluckco.sqwads.core.design.component.HyperlinkText
import com.psyluckco.sqwads.core.design.component.SqwadsProgressIndicator
import com.psyluckco.sqwads.core.design.theme.SqwadsTheme
import com.psyluckco.sqwads.core.model.LoadingState
import kotlinx.collections.immutable.persistentMapOf
import com.psyluckco.sqwads.core.design.R.string as AppText

@Composable
internal fun RegisterRoute(
    navigateToHome: (String) -> Unit,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navigationState by viewModel.navigationState.collectAsStateWithLifecycle()
    
    LaunchedEffect(key1 = navigationState) {
        fun performNavigation(action: () -> Unit) {
            action()
            viewModel.resetNavigation()
        }
        with(navigationState) {
            when(this) {
                is NavigationState.NavigateToHome -> performNavigation { navigateToHome(this.userId) }
                NavigationState.NavigateToLogin -> performNavigation(navigateToLogin)
                NavigationState.None -> Unit
            }
        }
    }

    when {
        uiState.loadingState is LoadingState.Loading -> {
            SqwadsProgressIndicator()
        }
    }

    RegisterScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        modifier = modifier
    )

}

@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    onEvent: (RegisterEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    val (
        userNameFocusRequester,
        emailFocusRequester,
        passwordFocusRequester,
        confirmPasswordFocusRequester
    ) = FocusRequester.createRefs()

    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    AppWrapper(modifier = modifier) {
        RegisterHeader()
        Spacer(modifier = Modifier.height(60.dp))
        TextFieldSection(
            uiState = uiState,
            onEvent = onEvent,
            userNameFocusRequester = userNameFocusRequester,
            emailFocusRequester = emailFocusRequester,
            passwordFocusRequester = passwordFocusRequester,
            confirmPasswordFocusRequester = confirmPasswordFocusRequester
        )
        Spacer(modifier = Modifier.height(60.dp))
        RegisterButtonSection(onEvent = onEvent)
    }

}

@Composable
fun RegisterHeader(modifier: Modifier = Modifier) {
    HeaderWrapper(modifier = modifier) {
        Text(
            text = stringResource(id = AppText.register_header),
            style = MaterialTheme.typography.displayMedium
        )
    }
}

@Composable
private fun TextFieldSection(
    uiState: RegisterUiState,
    onEvent: (RegisterEvent) -> Unit,
    userNameFocusRequester: FocusRequester,
    emailFocusRequester: FocusRequester,
    passwordFocusRequester: FocusRequester,
    confirmPasswordFocusRequester: FocusRequester,
    modifier: Modifier = Modifier
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        DefaultTextField(
            value = uiState.userName,
            label = AppText.placeholder,
            leadingIcon = Icons.Rounded.AccountCircle,
            onValueChange = { onEvent(RegisterEvent.OnUsernameChanged(it)) },
            modifier = Modifier.focusRequester(userNameFocusRequester)
        )
        DefaultTextField(
            value = uiState.email,
            label = AppText.placeholder,
            leadingIcon = Icons.Rounded.Email,
            onValueChange = { onEvent(RegisterEvent.OnEmailChanged(it)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.focusRequester(emailFocusRequester)
        )
        DefaultPasswordField(
            value = uiState.password,
            label = AppText.placeholder,
            leadingIcon = Icons.Rounded.Lock,
            onValueChange = { onEvent(RegisterEvent.OnPasswordChanged(it)) },
            modifier = Modifier.focusRequester(passwordFocusRequester)
        )
        DefaultPasswordField(
            value = uiState.confirmPassword,
            label = AppText.placeholder,
            leadingIcon = Icons.Rounded.Lock,
            onValueChange = { onEvent(RegisterEvent.OnConfirmPasswordChanged(it)) },
            modifier = Modifier.focusRequester(confirmPasswordFocusRequester)
        )
    }
}

@Composable
fun RegisterButtonSection(
    onEvent: (RegisterEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        DefaultTextButton(text = AppText.register_button) {
            onEvent(RegisterEvent.OnRegisterClicked)
        }
        Spacer(modifier = Modifier.height(28.dp))
        DefaultTextButton(text = AppText.placeholder) {
            onEvent(RegisterEvent.OnAlreadyHaveAccountClick)
        }
        HyperlinkText(
            fullText = "Already a user? Sign In.",
            hyperLinks = persistentMapOf()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    SqwadsTheme {
        RegisterScreen(
            uiState = RegisterUiState(
                userName = "add787",
                email = "adarshshetty787@gmail.com",
                password = "hooman@787",
                confirmPassword = "hooman@787"
            ),
            onEvent = { },
        )
    }
}