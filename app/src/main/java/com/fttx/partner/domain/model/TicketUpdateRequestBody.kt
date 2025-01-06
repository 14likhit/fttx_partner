package com.fttx.partner.domain.model

import com.google.gson.annotations.SerializedName

data class TicketUpdateRequestBody(
    @SerializedName("ticket_id")val ticketId: Int,
    @SerializedName("status")val status: String,
    @SerializedName("latitude")val latitude: Double,
    @SerializedName("longitude")val longitude: Double,
    @SerializedName("agent_ids")val agentIds: List<Int>
)
