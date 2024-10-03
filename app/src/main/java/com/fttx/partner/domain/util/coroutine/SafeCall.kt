package com.fttx.partner.domain.util.coroutine

import com.fttx.partner.R
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

inline fun <T> executeSafeCall(
    block: () -> T,
    error: (UiText) -> T,
): T {
    return try {
        block.invoke()
    } catch (e: Exception) {
        val textRes =
            when (e) {
                is SocketTimeoutException ->
                    UiText.StringResource(
                        R.string.error,
                    )

                is UnknownHostException ->
                    UiText.StringResource(
                        R.string.error,
                    )

                is IOException -> UiText.StringResource(R.string.error)
                else -> UiText.DynamicString(e.message.orEmpty())
            }
        error.invoke(textRes)
    }
}
