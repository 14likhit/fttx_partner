package com.fttx.partner.ui.compose.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomerUiModel(
    val id: Long,
    val name: String,
    val address: String,
    val phone: String,
    val lat: Double,
    val long: Double,
) : Parcelable
