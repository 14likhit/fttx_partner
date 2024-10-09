package com.fttx.partner.ui.compose.utils

import androidx.compose.runtime.compositionLocalOf

val LocalLocale =
    compositionLocalOf<String> { error("Locale is not provided") }
