package com.fttx.partner.domain.model

data class Ticket(
    val id: String,
    val title: String,
    val time: String,
    val category: String,
    val status: String,
    val customer: Customer,
)
