package com.fttx.partner.domain.util.coroutine

import android.content.Context
import android.content.res.Resources
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import com.fttx.partner.data.network.util.ErrorHandlerConstants.ERROR_GENERIC
import com.fttx.partner.data.network.util.HTTP_ERROR_BAD_REQUEST

sealed class UiText {
    data class DynamicString(val text: String) : UiText()

    data class StringResource(val resId: Int) : UiText()

    data class ErrorDynamicStringType(val text: String, val errorType: Int) :
        UiText()

    data class ErrorStringResourceType(val resId: Int, val errorType: Int) :
        UiText()

    data class DynamicErrorType(
        val text: String,
        val errorType: Int,
        val errorCode: Int = HTTP_ERROR_BAD_REQUEST,
    ) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> text
            is StringResource -> context.getString(resId)
            is ErrorDynamicStringType -> text
            is ErrorStringResourceType -> context.getString(resId)
            is DynamicErrorType -> text
        }
    }

    fun asErrorType(): Int {
        return when (this) {
            is DynamicString -> ERROR_GENERIC
            is ErrorDynamicStringType -> errorType
            is ErrorStringResourceType -> errorType
            is StringResource -> ERROR_GENERIC
            is DynamicErrorType -> errorType
        }
    }

    fun asErrorCode(): Int {
        return when (this) {
            is DynamicErrorType -> errorCode
            else -> HTTP_ERROR_BAD_REQUEST
        }
    }
}

sealed class TextResource {
    companion object {
        fun fromText(text: String): TextResource = SimpleTextResource(text)

        fun fromStringId(
            @StringRes id: Int,
        ): TextResource = IdTextResource(id)

        fun fromPlural(
            @PluralsRes id: Int,
            pluralValue: Int,
        ): TextResource = PluralTextResource(id, pluralValue)
    }
}

private data class SimpleTextResource(
    val text: String,
) : TextResource()

private data class IdTextResource(
    @StringRes val id: Int,
) : TextResource()

private data class PluralTextResource(
    @PluralsRes val pluralId: Int,
    val quantity: Int,
) : TextResource()

fun TextResource.asString(resources: Resources): String =
    when (this) {
        is SimpleTextResource -> this.text
        is IdTextResource -> resources.getString(this.id)
        is PluralTextResource ->
            resources.getQuantityString(
                this.pluralId,
                this.quantity,
            )
    }
