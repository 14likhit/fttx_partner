package com.fttx.partner.ui.screen.form

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.fttx.partner.domain.model.Ticket
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun TicketFormRoute(
    ticket: Ticket?,
    onBackPress: () -> Unit,
    ticketFormViewModel: TicketFormViewModel = hiltViewModel()
) {

    val uiState by ticketFormViewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        ticketFormViewModel.uiEffect.onEach {
            when (it) {
                TicketFormEffect.NavigateBack -> onBackPress()
                TicketFormEffect.NavigateToAddTicket -> {}
                is TicketFormEffect.NavigateToTicketDetails -> {}
            }
        }.collect()
    }

    TicketFormScreen(
        ticket = ticket,
        onTriggerIntent = {
            coroutineScope.launch {
                ticketFormViewModel.intents.send(it)
            }
        },
        uiState = uiState
    )
}