package com.fttx.partner.domain.model

data class UserTicket(
    val status: String,
    val user: User,
    val tickets: List<Ticket>,
)