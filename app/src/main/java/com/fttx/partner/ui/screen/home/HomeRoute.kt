package com.fttx.partner.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.fttx.partner.domain.model.Customer
import com.fttx.partner.domain.model.Ticket
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun HomeRoute(
    navigateToTicketFormActivity: (Ticket?, Customer?) -> Unit,
    navigateToAccountActivity: () -> Unit,
    onBackPress: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by homeViewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        homeViewModel.uiEffect.onEach {
            when (it) {
                HomeEffect.NavigateBack -> onBackPress()
                is HomeEffect.NavigateToAddTicket -> navigateToTicketFormActivity(null, it.customer)
                is HomeEffect.NavigateToTicketDetails -> navigateToTicketFormActivity(
                    it.ticket,
                    null
                )

                HomeEffect.NavigateToAccount -> navigateToAccountActivity()
            }
        }.collect()
    }

    HomeScreen(
        onTriggerIntent = {
            coroutineScope.launch {
                homeViewModel.intents.send(it)
            }
        },
        uiState = uiState
    )
}