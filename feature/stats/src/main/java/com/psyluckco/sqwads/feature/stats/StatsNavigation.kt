/**
 * Created by developer on 01-01-2025.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.feature.stats

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

data object Stats

fun NavController.navigateToStats(
    navOptions: NavOptions? = null
) {
    navigate(Stats, navOptions)
}

fun NavGraphBuilder.statsScreen(
    popup: () -> Unit
) {
    composable<Stats> {

    }
}

