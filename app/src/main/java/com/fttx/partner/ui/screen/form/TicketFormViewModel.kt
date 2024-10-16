package com.fttx.partner.ui.screen.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fttx.partner.domain.usecase.ticket.GetTicketUseCase
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
    private val getTicketUseCase: GetTicketUseCase
) : ViewModel(), IModel<TicketFormState, TicketFormIntent, TicketFormEffect> {

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
                }
            }
        }
    }
}