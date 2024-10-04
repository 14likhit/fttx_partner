package com.fttx.partner.ui.screen.calender

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
    ticketViewModel:TicketViewModel = hiltViewModel()
) {

    val uiState by ticketViewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        ticketViewModel.uiEffect.onEach {
            when(it){
                TicketEffect.NavigateToAddTicket -> {}
            }
        }
    }

    CalenderScreen(
        onTriggerIntent = {
            coroutineScope.launch {
                ticketViewModel.intents.send(it)
            }
        },
        uiState = uiState
    )
}