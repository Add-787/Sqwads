/**
 * Created by developer on 12-12-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.core.design.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.psyluckco.sqwads.core.design.R.string as AppText
import com.psyluckco.sqwads.core.design.theme.SqwadsTheme

@Composable
fun DefaultDialog(
    @StringRes title: Int,
    content: @Composable () -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(17.dp)
    ) {

        Column(
            modifier = modifier.padding(24.dp)
        ) {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.displayMedium,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier.weight(1f)
            ) {
                content()
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
            ) {
                DefaultTextButton(
                    modifier = Modifier.width(120.dp),
                    text = AppText.dismiss_button,
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                )
                DefaultTextButton(
                    modifier = Modifier.width(120.dp),
                    text = AppText.confirm_button,
                    onClick = onConfirm
                )
            }
        }

    }

}

@Composable
fun DefaultEditRoomDialog(
    @StringRes title: Int = AppText.placeholder,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {

    val (text,onValueChange) = rememberSaveable { mutableStateOf("") }
    val (isError, setIsError) = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = text.isNotEmpty()) {
        if(text.isNotBlank()) setIsError(false)
    }

    DefaultDialog(
        title = title,
        content = {

            Column {

                Text(text = stringResource(id = AppText.edit_room_dialog_section_text))

                DefaultTextField(
                    value = text,
                    label = AppText.edit_room_field_label_text,
                    leadingIcon = Icons.Default.MeetingRoom,
                    onValueChange = onValueChange,
                    isError = isError
                )
            }

        },
        onConfirm = {
            if(text.isEmpty()) {
                setIsError(false)
            } else {
                onConfirm(text)
            }
        },
        onDismiss = onDismiss
    )
    
}

@Preview
@Composable
private fun DefaultEditRoomDialogPreview() {
    SqwadsTheme {
        DefaultEditRoomDialog(
            title = AppText.placeholder,
            onConfirm = { },
            onDismiss = { }
        )
        
    }
}

@Preview
@Composable
private fun DefaultDialogPreview() {

    SqwadsTheme {
        DefaultDialog(
            title = AppText.placeholder,
            content = {
                Text(text = stringResource(id = AppText.placeholder_section_text))
            },
            onConfirm = { /*TODO*/ },
            onDismiss = { /*TODO*/ }
        )

    }

}