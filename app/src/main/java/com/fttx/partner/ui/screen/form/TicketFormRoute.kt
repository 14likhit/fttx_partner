package com.fttx.partner.ui.screen.form

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.fttx.partner.domain.model.Customer
import com.fttx.partner.domain.model.Ticket
import com.fttx.partner.ui.screen.employee.EmployeeSelectionScreen
import com.fttx.partner.ui.utils.location.getCurrentLocation
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketFormRoute(
    ticket: Ticket?,
    customer: Customer?,
    onBackPress: (Boolean) -> Unit,
    ticketFormViewModel: TicketFormViewModel = hiltViewModel()
) {

    val uiState by ticketFormViewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        ticketFormViewModel.uiEffect.onEach {
            when (it) {
                TicketFormEffect.NavigateBack -> onBackPress(false)
                TicketFormEffect.NavigateToAddTicket -> {}
                is TicketFormEffect.NavigateToTicketDetails -> {}
                TicketFormEffect.NavigateToTicketList -> onBackPress(true)
                is TicketFormEffect.FetchLocation -> {
                    getCurrentLocation(context,
                        onGetCurrentLocationSuccess = { location ->
                            coroutineScope.launch {
                                ticketFormViewModel.intents.send(
                                    TicketFormIntent.UpdateTicket(
                                        it.ticketId,
                                        it.ticketStatus,
                                        location
                                    )
                                )
                            }
                        },
                        onGetLastLocationIsNull = {

                        },
                        onGetCurrentLocationFailed = {

                        })
                }
            }
        }.collect()
    }

    TicketFormScreen(
        ticket = ticket,
        customer = customer,
        onTriggerIntent = {
            coroutineScope.launch {
                ticketFormViewModel.intents.send(it)
            }
        },
        uiState = uiState
    )

    if (uiState.isLoading) {
        Dialog(
            onDismissRequest = { },
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                CircularProgressIndicator()
            }
        }
    }
    if (uiState.error.isNotBlank()) {
        Toast.makeText(context, uiState.error, Toast.LENGTH_SHORT).show()
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                ticketFormViewModel.intents.send(
                    TicketFormIntent.EmptyError
                )
            }
        }
    }
    if (uiState.showAgentBottomSheet) {
        ModalBottomSheet(onDismissRequest = {
            coroutineScope.launch {
                ticketFormViewModel.intents.send(TicketFormIntent.DismissAgentBottomSheet)
            }
        }) {
            EmployeeSelectionScreen(
                users = uiState.allAgents,
                selectedUsers = uiState.selectedAgents,
                onClick = { selectedAgents ->
                    coroutineScope.launch {
                        ticketFormViewModel.intents.send(
                            TicketFormIntent.SaveAssociateCta(
                                selectedAgents
                            )
                        )
                    }
                }
            )
        }
    }
}