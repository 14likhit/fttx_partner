package com.fttx.partner.data.model

data class CustomerDto(
    val id: Long,
    val name: String,
    val address: String,
    val phone: String,
    val lat: Double,
    val long: Double,
)
