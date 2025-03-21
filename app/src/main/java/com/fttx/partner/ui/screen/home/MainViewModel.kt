package com.fttx.partner.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class MainViewModel @Inject constructor() : ViewModel(), IModel<MainState, MainIntent, MainEffect> {

    override val intents: Channel<MainIntent> = Channel(Channel.UNLIMITED)

    private val _uiState = MutableStateFlow(MainState())
    override val uiState: StateFlow<MainState>
        get() = _uiState.asStateFlow()

    private val _uiEffect = Channel<MainEffect>()
    override val uiEffect: Flow<MainEffect>
        get() = _uiEffect.receiveAsFlow()

    init {
        handleIntent()
    }

    override fun handleIntent() {
        viewModelScope.launch {
            intents.receiveAsFlow().collect {
                when (it) {
                    MainIntent.CheckIn -> {
                        _uiState.value =
                            _uiState.value.copy(isCheckedIn = true, isCheckedOut = false)
                    }

                    MainIntent.CheckOut -> {
                        Log.e("Test","Checkout")
                        _uiState.value =
                            _uiState.value.copy(isCheckedOut = true, isCheckedIn = false)
                    }
                }
            }
        }
    }

    fun checkIn() {
        viewModelScope.launch {
            intents.send(MainIntent.CheckIn)
        }
    }

    fun checkOut() {
        viewModelScope.launch {
            intents.send(MainIntent.CheckOut)
        }
    }

    fun hideProgress() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(hideProgress = true)
        }
    }
}