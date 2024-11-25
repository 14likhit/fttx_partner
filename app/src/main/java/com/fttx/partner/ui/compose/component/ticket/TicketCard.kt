package com.fttx.partner.ui.compose.component.ticket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fttx.partner.domain.model.Ticket
import com.fttx.partner.ui.compose.model.TicketStatusUiModel
import com.fttx.partner.ui.compose.theme.FTTXPartnerTheme
import com.fttx.partner.ui.compose.theme.Grape70
import com.fttx.partner.ui.compose.theme.Subheading01Bold
import com.fttx.partner.ui.compose.theme.Text01Bold
import com.fttx.partner.ui.compose.theme.Text01Regular
import com.fttx.partner.ui.mock.getTicket
import com.fttx.partner.ui.utils.clickable

@Composable
fun TicketCard(
    ticket: Ticket,
    modifier: Modifier = Modifier,
    onCardClick: (Ticket) -> Unit = {},
    onCallClick: (Ticket) -> Unit = {},
) {
    OutlinedCard(
        onClick = { onCardClick(ticket) },
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Row {
                Text(
                    text = ticket.id,
                    style = Text01Bold,
                    modifier = Modifier.weight(1f)
                )
                val ticketStatus = TicketStatusUiModel.fromStatus(ticket.status)
                Text(
                    text = ticketStatus.status,
                    style = Text01Bold,
                    color = ticketStatus.textColor,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .background(
                            ticketStatus.backgroundColor,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 16.dp)
                )
                Box(
                    modifier = Modifier
                        .background(Color.Red)
                        .size(12.dp)
                        .align(Alignment.CenterVertically)
                )
            }
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(vertical = 8.dp)
                    .height(IntrinsicSize.Min)
            ) {
                Text(
                    text = ticket.ticketType,
                    style = Subheading01Bold,
                    color = Grape70,
                    modifier = Modifier.weight(1f)
                )
            }
            Text(
                text = ticket.title,
                modifier = Modifier.padding(vertical = 8.dp),
                style = Text01Regular
            )
            HorizontalDivider()
            Text(
                text = ticket.customerName,
                style = Text01Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Row {
                Text(text = ticket.customerAddress, modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "call",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(26.dp)
                        .clickable {
                            onCallClick(ticket)
                        },
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