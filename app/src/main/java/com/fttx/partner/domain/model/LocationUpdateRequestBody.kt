package com.fttx.partner.domain.model

import com.google.gson.annotations.SerializedName

data class LocationUpdateRequestBody(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("agent_id") val agentId: Int,
    @SerializedName("action") val action: String = Action.CheckIn.value,
)

enum class Action(val value: String) {
    CheckIn("checkin"),
    CheckOut("checkout"),
    LocationUpdate("location_update"),
}
