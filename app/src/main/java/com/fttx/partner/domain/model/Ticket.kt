package com.fttx.partner.domain.model

import android.os.Parcelable
import com.fttx.partner.data.model.AgentsDto
import com.fttx.partner.ui.compose.model.UserUiModel
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
) : Parcelable {
    fun matchAssociatedAgents(users: List<UserUiModel>): Boolean {
        val associateAgentsUiModel = associatedAgents.map { it.toUserUiModel() }
        return users.containsAll(associateAgentsUiModel) && associateAgentsUiModel.containsAll(users)
    }
}
