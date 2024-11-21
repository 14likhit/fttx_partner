package com.fttx.partner.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fttx.partner.data.network.util.SemaaiResult
import com.fttx.partner.data.source.local.datastore.DataStorePreferences
import com.fttx.partner.domain.usecase.login.LoginUseCase
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
    private val loginUseCase: LoginUseCase,
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
                    is LoginIntent.Init -> checkLogin()
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

    private suspend fun checkLogin() {
        if (dataStorePreferences.isUserLoggedIn()) {
            _uiState.value = _uiState.value.copy(isLoading = false, isLoggedIn = true)
            _uiEffect.send(LoginEffect.NavigateToHome)
        } else {
            _uiState.value = _uiState.value.copy(isLoading = false, isLoggedIn = false)
        }
    }

    private suspend fun checkCredentials(loginUiModel: LoginUiModel) {
        if (loginUiModel.isNotEmpty()) {
            _uiState.value = _uiState.value.copy(isLoading = true)
            viewModelScope.launch {
                when (val result = loginUseCase(loginUiModel.login, loginUiModel.pwd)) {
                    is SemaaiResult.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorLogin = "Invalid Credential"
                        )
                    }

                    is SemaaiResult.Success -> {
                        if (result.data.userId > 0) {
                            dataStorePreferences.setUserLoggedIn(true)
                            dataStorePreferences.saveUserId(result.data.userId)
                            _uiState.value = _uiState.value.copy(isLoading = true)
                            _uiEffect.send(LoginEffect.NavigateToHome)
                        } else {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                errorLogin = "Invalid Credential"
                            )
                        }
                    }
                }
            }
        } else {
            _uiState.value =
                _uiState.value.copy(isLoading = false, errorLogin = "Invalid Credential")
        }
    }
}