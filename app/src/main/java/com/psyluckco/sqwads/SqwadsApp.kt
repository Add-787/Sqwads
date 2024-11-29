/**
 * Created by developer on 07-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads

import android.content.res.Resources
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.psyluckco.sqwads.core.common.snackbar.SnackbarManager
import com.psyluckco.sqwads.feature.home.Home
import com.psyluckco.sqwads.feature.login.Login
import com.psyluckco.sqwads.navigation.SqwadsNavHost
import kotlinx.coroutines.CoroutineScope

@Composable
fun SqwadsApp(
    accountState: AccountState,
    appState: SqwadsAppState = rememberAppState()
) {

    val startDestination: Any = when(accountState) {
        AccountState.UserAlreadySignIn -> Home
        else -> Login
    }
    
    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = appState.snackbarHostState)
            },
            contentWindowInsets = WindowInsets(0,0,0,0)
        ) {
            SqwadsNavHost(
                modifier = Modifier.padding(it),
                appState = appState,
                startDestination = startDestination
            )
        }
    }



}

@Composable
fun resources() : Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(navController, snackbarHostState, coroutineScope) {
    SqwadsAppState(
        navHostController = navController,
        snackbarHostState = snackbarHostState,
        snackbarManager = snackbarManager,
        resources = resources,
        coroutineScope = coroutineScope
    )
}