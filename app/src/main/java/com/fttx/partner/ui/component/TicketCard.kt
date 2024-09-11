package com.fttx.partner.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fttx.partner.domain.model.Ticket
import com.fttx.partner.ui.mock.getTicket
import com.fttx.partner.ui.theme.FTTXPartnerTheme

@Composable
fun TicketCard(ticket: Ticket, modifier: Modifier = Modifier) {
    OutlinedCard(onClick = { }, modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Row {
                Text(text = ticket.category, modifier = Modifier.weight(1f))
                Text(text = ticket.status)
            }
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .height(IntrinsicSize.Min)
            ) {
                Text(text = ticket.id)
                VerticalDivider(modifier = Modifier.padding(horizontal = 8.dp))
                Text(text = ticket.time)
            }
            Text(text = ticket.title)
            HorizontalDivider()
            Text(text = ticket.customer.name)
            Row {
                Text(text = ticket.customer.address, modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "call",
                    tint = Color.Black,
                    modifier = Modifier.size(26.dp),
                )
            }
        }
    }

}

@Preview
@Composable
private fun TicketCardPreview(modifier: Modifier = Modifier) {
    FTTXPartnerTheme {
        TicketCard(ticket = getTicket())
    }
}