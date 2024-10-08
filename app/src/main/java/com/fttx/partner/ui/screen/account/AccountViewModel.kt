package com.fttx.partner.ui.screen.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fttx.partner.data.source.local.datastore.DataStorePreferences
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
class AccountViewModel @Inject constructor(
    private val dataStorePreferences: DataStorePreferences,
) : ViewModel(),
    IModel<AccountState, AccountIntent, AccountEffect> {

    override val intents: Channel<AccountIntent> = Channel(Channel.UNLIMITED)

    private val _uiState = MutableStateFlow(AccountState())
    override val uiState: StateFlow<AccountState>
        get() = _uiState.asStateFlow()

    private val _uiEffect = Channel<AccountEffect>()
    override val uiEffect: Flow<AccountEffect>
        get() = _uiEffect.receiveAsFlow()

    init {
        handleIntent()
    }

    override fun handleIntent() {
        viewModelScope.launch {
            intents.receiveAsFlow().collect {
                when (it) {
                    AccountIntent.LogoutCta -> handleLogout()
                }
            }
        }
    }

    private suspend fun handleLogout() {
        dataStorePreferences.setUserLoggedIn(false)
        dataStorePreferences.clearDataStore()
        _uiEffect.send(AccountEffect.NavigateToLoginScreen)
    }
}