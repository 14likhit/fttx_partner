package com.fttx.partner.data.model

data class TicketDto(
    val id: String,
    val title: String,
    val time: String,
    val category: String,
    val status: String,
    val customer: CustomerDto,
)
