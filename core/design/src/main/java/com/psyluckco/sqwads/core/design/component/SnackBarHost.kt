/**
 * Created by developer on 26-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.core.design.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class DefaultSnackbarVisuals(
    override val message: String,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
    override val duration: SnackbarDuration = if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite,
    // You can add custom things here (for you it's an icon)
    @DrawableRes val drawableRes: Int? = null
) : SnackbarVisuals

@Composable
fun DefaultSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        SnackbarHost(hostState = hostState) { snackbarData ->

            val visuals = snackbarData.visuals as DefaultSnackbarVisuals


            

        }
    }

}

@Composable
fun DefaultSnackbar(modifier: Modifier = Modifier) {
    Snackbar {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
        ) {

        }
    }

}