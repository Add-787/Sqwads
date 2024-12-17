package com.psyluckco.sqwads.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.psyluckco.sqwads.core.design.component.AppWrapper
import com.psyluckco.sqwads.core.design.theme.SqwadsTheme
import com.psyluckco.sqwads.core.model.LoadingState

@Composable
internal  fun ProfileRoute(
    viewModel: ProfileViewModel = hiltViewModel()
){
    ProfileScreen()
}

@Composable
fun ProfileScreen(
   modifier: Modifier = Modifier
){
    AppWrapper(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {

    }
}


@Composable
@Preview(showBackground = true)
fun ProfileScreenPreview(){
    SqwadsTheme {
        ProfileScreen()
    }
}