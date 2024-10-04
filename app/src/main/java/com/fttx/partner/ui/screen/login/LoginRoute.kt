package com.fttx.partner.ui.screen.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.fttx.partner.domain.model.Ticket
import com.fttx.partner.ui.screen.home.HomeEffect
import com.fttx.partner.ui.screen.home.HomeScreen
import com.fttx.partner.ui.screen.home.HomeViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun LoginRoute(
    navigateToHomeScreen: () -> Unit,
    onBackPress: () -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    val uiState by loginViewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        loginViewModel.uiEffect.onEach {
            when (it) {
                LoginEffect.NavigateBack -> onBackPress()
                LoginEffect.NavigateToHome -> navigateToHomeScreen()
            }
        }.collect()
    }

    LoginScreen(
        onTriggerIntent = {
            coroutineScope.launch {
                loginViewModel.intents.send(it)
            }
        },
        uiState = uiState,
    )
}