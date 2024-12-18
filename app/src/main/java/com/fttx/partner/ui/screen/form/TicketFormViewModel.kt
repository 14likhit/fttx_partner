package com.fttx.partner.ui.screen.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fttx.partner.data.network.util.SemaaiResult
import com.fttx.partner.domain.usecase.ticket.UpdateTicketUseCase
import com.fttx.partner.ui.mvicore.IModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketFormViewModel @Inject constructor(
    private val updateTicketUseCase: UpdateTicketUseCase,
) : ViewModel(),
    IModel<TicketFormState, TicketFormIntent, TicketFormEffect> {

    override val intents: Channel<TicketFormIntent> = Channel(Channel.UNLIMITED)

    private val _uiState = MutableStateFlow(TicketFormState())
    override val uiState: StateFlow<TicketFormState>
        get() = _uiState.asStateFlow()

    private val _uiEffect = Channel<TicketFormEffect>()
    override val uiEffect: Flow<TicketFormEffect>
        get() = _uiEffect.receiveAsFlow()

    init {
        handleIntent()
    }

    override fun handleIntent() {
        viewModelScope.launch {
            intents.receiveAsFlow().collect {
                when (it) {
                    is TicketFormIntent.Init -> {}
                    TicketFormIntent.AddCta -> {
                        _uiEffect.send(TicketFormEffect.NavigateToAddTicket)
                    }

                    TicketFormIntent.BackCta -> {
                        _uiEffect.send(TicketFormEffect.NavigateBack)
                    }

                    is TicketFormIntent.TicketCardCta -> {
                        _uiEffect.send(TicketFormEffect.NavigateToTicketDetails(it.ticket))
                    }

                    is TicketFormIntent.UpdateTicketCta -> {
                        getLocation(it.ticketId, it.ticketStatus)
                    }

                    is TicketFormIntent.UpdateTicket -> {
                        updateTicket(it.ticketId, it.ticketStatus, it.location)
                    }
                }
            }
        }
    }

    private suspend fun getLocation(ticketId: Int, ticketStatus: String) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        _uiEffect.send(TicketFormEffect.FetchLocation(ticketId, ticketStatus))
    }

    private suspend fun updateTicket(
        ticketId: Int,
        status: String,
        location: Pair<Double, Double>
    ) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        when (val result = updateTicketUseCase(ticketId, status, location)) {
            is SemaaiResult.Error -> {
                _uiState.value = _uiState.value.copy(isLoading = false, error = "Something went wrong")
            }

            is SemaaiResult.Success -> {
                _uiEffect.send(TicketFormEffect.NavigateToTicketList)
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}