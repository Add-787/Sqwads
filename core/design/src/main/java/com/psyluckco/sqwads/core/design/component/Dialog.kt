/**
 * Created by developer on 12-12-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.core.design.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.psyluckco.sqwads.core.design.R.string as AppText
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
            .height(400.dp)
            .padding(17.dp)
    ) {

        Column(
            modifier = modifier.padding(24.dp)
        ) {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Box(
                modifier = Modifier.weight(1f)
            ) {
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier.fillMaxWidth()
            ) {
                DefaultTextButton(
                    modifier = Modifier.width(90.dp),
                    text = AppText.dismiss_button,
                    onClick = onDismiss
                )
                DefaultTextButton(
                    modifier = Modifier.width(90.dp),
                    text = AppText.confirm_button,
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
            title = AppText.placeholder,
            onConfirm = { /*TODO*/ },
            onDismiss = { /*TODO*/ }
        )

    }

}