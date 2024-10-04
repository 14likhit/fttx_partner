package com.fttx.partner.ui.mvicore

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * Base Mode Interface to be implemented by every viewmodels
 */
interface IModel<S : IState, I : IIntent, E : IEffect> {
    val intents: Channel<I>
    val uiState: StateFlow<S>
    val uiEffect: Flow<E>

    fun handleIntent()
}
