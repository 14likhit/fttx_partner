package com.fttx.partner.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun CalenderRoute(
    navigateToTicketFormActivity: () -> Unit,
    onBackPress: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by homeViewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        homeViewModel.uiEffect.onEach {
            when (it) {
                HomeEffect.NavigateToAddHome -> {}
            }
        }
    }

    CalenderScreen(
        onTriggerIntent = {
            coroutineScope.launch {
                homeViewModel.intents.send(it)
            }
        },
        uiState = uiState
    )
}