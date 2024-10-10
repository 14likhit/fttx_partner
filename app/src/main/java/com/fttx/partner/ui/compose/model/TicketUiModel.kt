package com.fttx.partner.ui.compose.model

import android.os.Parcelable
import com.fttx.partner.domain.model.Ticket
import kotlinx.parcelize.Parcelize

@Parcelize
data class TicketUiModel(
    val id: String,
    val title: String,
    val time: String,
    val category: String,
    val status: TicketStatusUiModel,
    val customer: CustomerUiModel,
) : Parcelable

fun Ticket.toTicketUiModel(): TicketUiModel {
    return TicketUiModel(
        id = id,
        title = title,
        time = time,
        category = category,
        status = TicketStatusUiModel.valueOf(status),
        customer = CustomerUiModel(
            id = customer.id,
            name = customer.name,
            address = customer.address,
            phone = customer.phone,
            lat = customer.lat,
            long = customer.long,
        ),
    )
}
