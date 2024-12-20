package com.fttx.partner.ui.compose.component.ticket

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fttx.partner.domain.model.Ticket
import com.fttx.partner.ui.mock.getTickets
import com.fttx.partner.ui.compose.theme.FTTXPartnerTheme

@Composable
fun TicketList(
    tickets: List<Ticket>,
    modifier: Modifier = Modifier,
    onCardClick: (Ticket) -> Unit = {},
    onCallClick: (Ticket) -> Unit = {},
) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = 8.dp,
            bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
        ),
        modifier = modifier.padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items = tickets) { ticket ->
            TicketCard(
                ticket = ticket,
                onCardClick = onCardClick,
                onCallClick = onCallClick,
            )
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