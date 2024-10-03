package com.fttx.partner.data.network.util

sealed interface SemaaiResult<out S, out E> {
    data class Success<S>(val data: S) : SemaaiResult<S, Nothing>

    data class Error<E>(val error: E) : SemaaiResult<Nothing, E>
}
