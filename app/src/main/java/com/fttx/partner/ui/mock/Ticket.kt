package com.fttx.partner.ui.mock

import com.fttx.partner.domain.model.Ticket

fun getTickets() = List(12) {
    getTicket()
}

fun getTicket() = Ticket(
    id = "7109287789",
    title = "Dual Play- Repair",
    time = "10am",
    category = "Retail",
    status = "Assigned",
    customer = getCustomer(),
    priority = "High",
    customerName = "John Doe"
)