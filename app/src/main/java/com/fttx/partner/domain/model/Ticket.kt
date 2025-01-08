package com.fttx.partner.domain.model

import android.os.Parcelable
import com.fttx.partner.data.model.AgentsDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ticket(
    val id: String,
    val title: String,
    val description: String,
    val ticketType: String,
    val status: String,
    val priority: String,
    val customerName: String,
    val customerMobile: String,
    val customerPhone: String,
    val customerAddress: String,
    val associatedAgents: List<User>,
) : Parcelable
