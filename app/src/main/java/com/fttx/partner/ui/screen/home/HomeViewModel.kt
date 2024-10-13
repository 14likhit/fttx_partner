package com.fttx.partner.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fttx.partner.domain.usecase.GetTicketUseCase
import com.fttx.partner.ui.mock.getCustomer
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
class HomeViewModel @Inject constructor(
    private val getTicketUseCase: GetTicketUseCase
) : ViewModel(), IModel<HomeState, HomeIntent, HomeEffect> {

    override val intents: Channel<HomeIntent> = Channel(Channel.UNLIMITED)

    private val _uiState = MutableStateFlow(HomeState())
    override val uiState: StateFlow<HomeState>
        get() = _uiState.asStateFlow()

    private val _uiEffect = Channel<HomeEffect>()
    override val uiEffect: Flow<HomeEffect>
        get() = _uiEffect.receiveAsFlow()

    init {
        handleIntent()
    }

    override fun handleIntent() {
        viewModelScope.launch {
            intents.receiveAsFlow().collect {
                when (it) {
                    is HomeIntent.Init -> {}
                    HomeIntent.AddCta -> {
                        _uiEffect.send(HomeEffect.NavigateToAddTicket(getCustomer()))
                    }

                    HomeIntent.BackCta -> {
                        _uiEffect.send(HomeEffect.NavigateBack)
                    }

                    is HomeIntent.TicketCardCta -> {
                        _uiEffect.send(HomeEffect.NavigateToTicketDetails(it.ticket))
                    }

                    HomeIntent.AccountCta -> {
                        _uiEffect.send(HomeEffect.NavigateToAccount)
                    }

                    is HomeIntent.PhoneCta -> {
                        _uiEffect.send(HomeEffect.NavigateToCall(it.ticket))
                    }
                }
            }
        }
    }
}