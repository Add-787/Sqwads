package com.psyluckco.sqwads 

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.psyluckco.sqwads.core.design.theme.SqwadsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        viewModel.initialize()

        var isEmailVerified by  mutableStateOf<AccountState>(AccountState.Loading)

        scopeWithLifecycle {
            viewModel.accountState.filter { it != AccountState.Loading }.first().let {
                state -> isEmailVerified = state
            }
        }

        splashScreen.setKeepOnScreenCondition {
            isEmailVerified == AccountState.Loading
        }

        enableEdgeToEdge()
        setContent {
            SqwadsTheme {
                if(isEmailVerified != AccountState.Loading) {
                    SqwadsApp(accountState = isEmailVerified)
                }
            }
        }
    }

    private fun scopeWithLifecycle(block: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block = block)
        }
    }

}
