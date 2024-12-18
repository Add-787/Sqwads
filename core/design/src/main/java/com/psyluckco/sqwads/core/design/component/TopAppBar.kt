/**
 * Created by developer on 08-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.core.design.component

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CatchingPokemon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.psyluckco.sqwads.core.design.R
import com.psyluckco.sqwads.core.design.R.string as AppText
import com.psyluckco.sqwads.core.design.theme.SqwadsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SqwadsTopAppBar(
    @StringRes textId: Int,
    showNavigationIcon: Boolean = false,
    onBackClicked: () ->  Unit = {},
    showActionIcon: Boolean = false,
) {
    TopAppBar(
        title = { Text(stringResource(id = textId)) },
        navigationIcon = {
            if(showNavigationIcon)
                IconButton(onClick = onBackClicked) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null)
                }
        },
        actions = {
            if(showActionIcon)
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.CatchingPokemon,
                        contentDescription = null)
                }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    )

}

@Preview(showBackground = true)
@Composable
private fun SqwadsAppBarPreview() {
    SqwadsTheme {
        SqwadsTopAppBar(
            textId = AppText.app_name
        )
    }

}