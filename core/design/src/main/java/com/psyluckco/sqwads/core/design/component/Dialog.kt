/**
 * Created by developer on 12-12-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.core.design.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.psyluckco.sqwads.core.design.R
import com.psyluckco.sqwads.core.design.theme.SqwadsTheme

@Composable
fun DefaultDialog(
    @StringRes title: Int,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(17.dp)
    ) {

        Column(
            modifier = modifier.padding(12.dp)
        ) {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Row {
                DefaultTextButton(
                    text = R.string.placeholder,
                    onClick = onConfirm
                )
            }
        }

    }

}

@Preview
@Composable
private fun DefaultDialogPreview() {

    SqwadsTheme {
        DefaultDialog(
            title = R.string.placeholder,
            onConfirm = { /*TODO*/ },
            onDismiss = { /*TODO*/ }
        )

    }

}