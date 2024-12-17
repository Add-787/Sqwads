package com.psyluckco.sqwads.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.psyluckco.sqwads.core.design.component.AppWrapper
import com.psyluckco.sqwads.core.design.theme.SqwadsTheme
import com.psyluckco.sqwads.core.model.LoadingState

@Composable
internal  fun ProfileRoute(
    viewModel: ProfileViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navigationState by viewModel.navigationState.collectAsStateWithLifecycle()


    ProfileScreen()
}

@Composable
fun ProfileScreen(
   modifier: Modifier = Modifier
){
    AppWrapper(modifier = modifier.background(MaterialTheme.colorScheme.background)) {

    }
}


@Composable
@Preview(showBackground = true)
fun ProfileScreenPreview(){
    SqwadsTheme {
        ProfileScreen()
    }
}