package com.fttx.partner.ui.screen.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun AccountRoute(
    onLogout: () -> Unit,
    onBackPress: () -> Unit,
    accountViewModel: AccountViewModel = hiltViewModel()
) {

    val uiState by accountViewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        accountViewModel.uiEffect.onEach {
            when (it) {
                AccountEffect.NavigateToLoginScreen -> onLogout()
                AccountEffect.NavigateBack -> onBackPress()
            }
        }.collect()
    }

    AccountScreen(
        onTriggerIntent = {
            coroutineScope.launch {
                accountViewModel.intents.send(it)
            }
        },
        uiState = uiState
    )
}