package com.fttx.partner.ui.screen.calender

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fttx.partner.domain.usecase.GetTicketUseCase
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
class TicketViewModel @Inject constructor(
    private val getTicketUseCase: GetTicketUseCase
) : ViewModel(), IModel<TicketState, TicketIntent, TicketEffect> {

    override val intents: Channel<TicketIntent> = Channel(Channel.UNLIMITED)

    private val _uiState = MutableStateFlow(TicketState())
    override val uiState: StateFlow<TicketState>
        get() = _uiState.asStateFlow()

    private val _uiEffect = Channel<TicketEffect>()
    override val uiEffect: Flow<TicketEffect>
        get() = _uiEffect.receiveAsFlow()

    init {
        handleIntent()
    }

    override fun handleIntent() {
        viewModelScope.launch {
            intents.receiveAsFlow().collect {
                when (it) {
                    is TicketIntent.Init -> {}
                }
            }
        }
    }
}