package com.fttx.partner.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Customer(
    val id: Long,
    val name: String,
    val address: String,
    val phone: String,
    val lat: Double,
    val long: Double,
) : Parcelable
