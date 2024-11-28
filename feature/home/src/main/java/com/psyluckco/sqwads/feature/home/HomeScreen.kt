package com.psyluckco.sqwads.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.psyluckco.sqwads.core.design.component.SqwadsProgressLoadingDialog

@Composable
internal fun HomeRoute(
    navigateToSquad: (String) -> Unit,
    navigateToProfile: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    /**
     * Uses [rememberUpdatedState] for `onEvent` to prevent unnecessary recompositions by maintaining
     * a stable reference. This method ensures minimal performance impact and efficient UI updates by
     * allowing `onEvent` to adapt dynamically to state changes without causing full screen
     * recompositions, thus optimizing UI responsiveness and performance.
     */
    val onEvent by rememberUpdatedState(
        newValue = { event: HomeUiEvent ->
            viewModel.onEvent(event)
        }
    )

    Scaffold(
        modifier = modifier
            .padding(bottom = 10.dp)
    ) { innerPadding ->

        Box(modifier = modifier.padding(innerPadding)) {

            if(uiState.isLoading) {
                SqwadsProgressLoadingDialog(id = com.psyluckco.sqwads.core.design.R.string.placeholder)
            }
            HomeScreen(uiState = uiState, onEvent = { event -> onEvent(event) })
        }
    }
    

}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "Welcome to Home Screen\nUser ID:",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreen(
        uiState = HomeUiState(isLoading = false),
        onEvent = {}
    )
}
