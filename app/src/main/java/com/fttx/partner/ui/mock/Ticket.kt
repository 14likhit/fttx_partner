package com.fttx.partner.ui.mock

import com.fttx.partner.domain.model.Ticket

fun getTickets() = List(12) {
    getTicket()
}

fun getTicket() = Ticket(
    id = "7109287789",
    title = "Dual Play- Repair",
    description = "Test",
    ticketType = "Retail",
    status = "Assigned",
    priority = "High",
    customerName = "John Doe",
    customerAddress = "address",
    customerMobile = "9405941144",
    customerPhone = "9405941144",
    associatedAgents = emptyList()
)