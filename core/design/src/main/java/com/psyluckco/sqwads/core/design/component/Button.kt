/**
 * Created by developer on 04-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.core.design.component

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CatchingPokemon
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.psyluckco.sqwads.core.design.IconType
import com.psyluckco.sqwads.core.design.R
import com.psyluckco.sqwads.core.design.theme.SqwadsTheme

@Composable
fun DefaultActionButton(
    iconType : IconType,
    @StringRes label : Int? = null,
    buttonSize: Dp = 100.dp,
    onClick: () -> Unit,
) {
    OutlinedButton(onClick = onClick, modifier = Modifier.size(buttonSize), shape = RoundedCornerShape(16.dp)) {
        when(iconType) {
            is IconType.Bitmap -> {
                Image(
                    painter = painterResource(id = iconType.painterId),
                    contentDescription = null
                )
            }
            is IconType.Vector -> {
                Image(
                    imageVector = iconType.imageVector,
                    contentDescription = null
                )
            }
        }
        Spacer(Modifier.width(8.dp))
        if(label != null) {
            Text(stringResource(label), style = MaterialTheme.typography.titleMedium)
        }

    }

}

@Composable
fun DefaultTextButton(
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    @StringRes text: Int,
    onClick: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedButton(
        onClick = {
            keyboardController?.hide()
            onClick()
        },
        shape = RoundedCornerShape(size = 16.dp),
        modifier = modifier.defaultMinSize(minWidth = 140.dp, minHeight = 65.dp),
        border = ButtonDefaults.outlinedButtonBorder.copy(width = 0.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimary

        ),
        contentPadding = PaddingValues(horizontal = 8.dp),

    ) {
        if(leadingIcon != null) {
            Icon(imageVector = leadingIcon, contentDescription = null, modifier = Modifier
                .size(40.dp)
                .padding(end = 5.dp))
        }
        Text(style = MaterialTheme.typography.labelLarge, text = stringResource(id = text))

    }
}


@Preview
@Composable
private fun DefaultActionButtonPreview() {
    SqwadsTheme {
        DefaultActionButton(
            iconType = IconType.Bitmap(painterId = R.drawable.google),
            onClick = { }
        )

    }
}
@Preview
@Composable
private fun DefaultTextButtonPreview() {
    SqwadsTheme {
        DefaultTextButton(text = R.string.placeholder, modifier = Modifier.fillMaxWidth()) {

        }
    }
}

@Preview
@Composable
private fun DefaultIconTextButtonPreview() {
    SqwadsTheme {
        DefaultTextButton(text = R.string.placeholder, leadingIcon = Icons.Default.CatchingPokemon) {
        }
    }
}


