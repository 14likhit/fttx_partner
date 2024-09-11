package com.fttx.partner.domain.model

data class Customer(
    val id: Long,
    val name: String,
    val address: String,
    val phone: String,
    val lat: Double,
    val long: Double,
)
