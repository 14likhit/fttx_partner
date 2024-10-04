package com.fttx.partner.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fttx.partner.data.source.local.datastore.DataStorePreferences
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
class LoginViewModel @Inject constructor(
    private val getTicketUseCase: GetTicketUseCase,
    private val dataStorePreferences: DataStorePreferences,
) : ViewModel(), IModel<LoginState, LoginIntent, LoginEffect> {

    override val intents: Channel<LoginIntent> = Channel(Channel.UNLIMITED)

    private val _uiState = MutableStateFlow(LoginState())
    override val uiState: StateFlow<LoginState>
        get() = _uiState.asStateFlow()

    private val _uiEffect = Channel<LoginEffect>()
    override val uiEffect: Flow<LoginEffect>
        get() = _uiEffect.receiveAsFlow()

    init {
        handleIntent()
    }

    override fun handleIntent() {
        viewModelScope.launch {
            intents.receiveAsFlow().collect {
                when (it) {
                    is LoginIntent.Init -> {}
                    LoginIntent.LoginCta -> {
                        _uiEffect.send(LoginEffect.NavigateToHome)
                    }

                    LoginIntent.BackCta -> {
                        _uiEffect.send(LoginEffect.NavigateBack)
                    }
                }
            }
        }
    }
}