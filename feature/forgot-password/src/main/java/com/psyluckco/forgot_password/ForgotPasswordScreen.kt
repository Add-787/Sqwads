package com.psyluckco.forgot_password

import android.preference.PreferenceActivity.Header
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.psyluckco.sqwads.core.design.Constants
import com.psyluckco.sqwads.core.design.R
import com.psyluckco.sqwads.core.design.component.AppWrapper
import com.psyluckco.sqwads.core.design.component.DayHeader
import com.psyluckco.sqwads.core.design.component.DefaultClickableLink
import com.psyluckco.sqwads.core.design.component.DefaultTextButton
import com.psyluckco.sqwads.core.design.component.DefaultTextField
import com.psyluckco.sqwads.core.design.component.HeaderWrapper
import com.psyluckco.sqwads.core.design.component.SqwadsProgressIndicator
import com.psyluckco.sqwads.core.design.component.SqwadsProgressLoadingDialog
import com.psyluckco.sqwads.core.design.theme.SqwadsTheme
import com.psyluckco.sqwads.core.model.LoadingState

@Composable
internal fun ForgotPasswordRoute(
    clearAndNavigateLogin: () -> Unit,
    viewModel: ForgotPasswordViewModel = hiltViewModel()
) {

    val email by viewModel.email.collectAsStateWithLifecycle()
    val loadingState by viewModel.loadingState.collectAsStateWithLifecycle()

    if(loadingState is LoadingState.Loading) {
        SqwadsProgressLoadingDialog(id = R.string.placeholder)
    }

    ForgotPasswordScreen(
        email,
        onEmailChange = viewModel::onEmailChange,
        onResetPasswordClick = { viewModel.sendPasswordResetEmail { clearAndNavigateLogin() }},
        backToLoginClick = { clearAndNavigateLogin() }
    )

}

@Composable
fun ForgotPasswordScreen(
    email: String,
    onEmailChange: (String) -> Unit,
    onResetPasswordClick: () -> Unit,
    backToLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    AppWrapper(modifier = Modifier.fillMaxSize()) {
        ForgotPasswordHeader()

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(64.dp)
        ) {

            Spacer(modifier = Modifier.height(45.dp))
            
            DefaultTextField(
                value = email,
                label = R.string.email_label,
                leadingIcon = Icons.Default.Email,
                onValueChange = onEmailChange
            )

            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DefaultTextButton(
                    text = R.string.password_recovery_button,
                    modifier = modifier.fillMaxWidth(),
                    onClick = onResetPasswordClick
                )
                DayHeader(dayString = "Or you can")
                DefaultTextButton(
                    leadingIcon = Icons.Default.ArrowBack,
                    text = R.string.back_to_login_button,
                    modifier = modifier.fillMaxWidth(),
                    onClick = backToLoginClick
                )
            }

        }

    }

}

@Composable
fun ForgotPasswordHeader(
    modifier: Modifier = Modifier
) {
    HeaderWrapper(
        modifier = modifier.fillMaxWidth()
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            Text(
                text = stringResource(R.string.forgot_password_header),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(9.dp))
            Text(
                text = stringResource(id = R.string.forgot_password_sub_header),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )

        }

    }
}

@Preview(showBackground = true)
@Composable
private fun ForgotPasswordScreenPreview() {
    SqwadsTheme {
        ForgotPasswordScreen(
            email = "jdbjn@email.com",
            onEmailChange = { },
            onResetPasswordClick = { },
            backToLoginClick = { })
    }
}