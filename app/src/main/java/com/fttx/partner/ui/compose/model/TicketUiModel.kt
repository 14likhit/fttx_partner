package com.fttx.partner.ui.compose.model

import android.os.Parcelable
import com.fttx.partner.domain.model.Ticket
import kotlinx.parcelize.Parcelize

@Parcelize
data class TicketUiModel(
    val id: String,
    val title: String,
    val status: TicketStatusUiModel,
) : Parcelable

fun Ticket.toTicketUiModel(): TicketUiModel {
    return TicketUiModel(
        id = id,
        title = title,
        status = TicketStatusUiModel.valueOf(status),
    )
}
