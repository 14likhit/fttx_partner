package com.fttx.partner.ui.screen.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fttx.partner.data.source.local.datastore.DataStorePreferences
import com.fttx.partner.domain.usecase.GetTicketUseCase
import com.fttx.partner.ui.mvicore.IModel
import com.fttx.partner.ui.screen.home.MainActivity
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
                    is LoginIntent.LoginCta -> {
                        checkCredentials(it.loginUiModel)
                    }

                    LoginIntent.BackCta -> {
                        _uiEffect.send(LoginEffect.NavigateBack)
                    }
                }
            }
        }
    }

    private suspend fun checkCredentials(loginUiModel: LoginUiModel){
        if (loginUiModel.isNotEmpty()) {
            _uiEffect.send(LoginEffect.NavigateToHome)
        } else {
            _uiState.value = _uiState.value.copy(errorLogin = "Invalid Credential")
        }
    }
}