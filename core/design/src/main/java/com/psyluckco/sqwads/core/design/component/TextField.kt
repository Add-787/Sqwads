/**
 * Created by developer on 29-10-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.core.design.component

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CatchingPokemon
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.psyluckco.sqwads.core.design.R
import com.psyluckco.sqwads.core.design.theme.SqwadsTheme

@Composable
fun DefaultTextField(
    value: String,
    @StringRes label: Int,
    leadingIcon: ImageVector,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    colors: TextFieldColors = TextFieldDefaults.colors(
        unfocusedContainerColor = MaterialTheme.colorScheme.background,
        focusedContainerColor =  MaterialTheme.colorScheme.background
    ),
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        )
    ) {

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(id = label)) },
        shape = RoundedCornerShape(8.dp),
        leadingIcon = {
            Icon(imageVector = leadingIcon, contentDescription = null)
        },
        trailingIcon = {

        },
        colors = colors,
        enabled = enabled,
        isError = isError,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions
    )

}

@Composable
fun DefaultPasswordField(
    value: String,
    @StringRes label: Int,
    leadingIcon: ImageVector,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    colors: TextFieldColors = TextFieldDefaults.colors(
        unfocusedContainerColor = MaterialTheme.colorScheme.background,
        focusedContainerColor =  MaterialTheme.colorScheme.background
    ),
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next,
        keyboardType = KeyboardType.Password
    )
) {

    val showPasswordVisibility by remember(value) { derivedStateOf { value.isNotEmpty() }}
    var passwordVisibility by remember(showPasswordVisibility) { mutableStateOf(value = false) }


    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(id = label)) },
        shape = RoundedCornerShape(8.dp),
        leadingIcon = {
            Icon(imageVector = leadingIcon, contentDescription = null)
        },
        trailingIcon = {
            if(showPasswordVisibility) {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            }

        },
        enabled = enabled,
        isError = isError,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        visualTransformation = if(passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation()
    )

}

@Composable
@Preview(showBackground = true)
private fun TextFieldPreview() {
    SqwadsTheme {
        DefaultTextField(value = "", label = R.string.placeholder, leadingIcon = Icons.Default.CatchingPokemon , onValueChange = {})
    }
}

@Composable
@Preview(showBackground = true)
private fun PasswordFieldPreview() {
    SqwadsTheme {
        DefaultPasswordField(value = "", label = R.string.placeholder, leadingIcon = Icons.Default.Password , onValueChange = {})
    }
}
