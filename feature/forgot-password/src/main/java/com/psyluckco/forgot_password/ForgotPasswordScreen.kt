package com.psyluckco.forgot_password

import android.preference.PreferenceActivity.Header
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
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
import com.psyluckco.sqwads.core.design.theme.SqwadsTheme

@Composable
internal fun ForgotPasswordRoute(
    clearAndNavigateLogin: () -> Unit,
    viewModel: ForgotPasswordViewModel = hiltViewModel()
) {

    val email by viewModel.email.collectAsStateWithLifecycle()

    val loadingState by viewModel.loadingState.collectAsStateWithLifecycle()

}

@Composable
fun ForgotPasswordScreen(
    email: String,
    onEmailChange: (String) -> Unit,
    onResetPasswordClick: () -> Unit,
    backToLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    AppWrapper {
        ForgotPasswordHeader()

        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            
            DefaultTextField(
                value = email,
                label = R.string.placeholder,
                leadingIcon = Icons.Default.Email,
                onValueChange = onEmailChange
            )

            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DefaultTextButton(
                    text = R.string.placeholder,
                    modifier = modifier.fillMaxWidth()
                ) {
                    
                }
                DayHeader(dayString = "Or you can")
                DefaultTextButton(
                    text = R.string.placeholder,
                    modifier = modifier.fillMaxWidth()
                ) {

                }
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