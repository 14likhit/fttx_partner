package com.fttx.partner.ui.component.ticket

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fttx.partner.domain.model.Ticket
import com.fttx.partner.ui.mock.getTickets
import com.fttx.partner.ui.theme.FTTXPartnerTheme

@Composable
fun TicketList(tickets: List<Ticket>, modifier: Modifier = Modifier) {
    LazyColumn {
        items(items = tickets) { ticket ->
            TicketCard(ticket = ticket)
        }
    }
}

@Preview
@Composable
private fun TicketListPreview(modifier: Modifier = Modifier) {
    FTTXPartnerTheme {
        TicketList(tickets = getTickets())
    }
}