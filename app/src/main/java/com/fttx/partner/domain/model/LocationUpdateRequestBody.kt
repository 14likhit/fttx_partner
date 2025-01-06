package com.fttx.partner.domain.model

import com.google.gson.annotations.SerializedName

data class LocationUpdateRequestBody(
    @SerializedName("latitude")val latitude: Double,
    @SerializedName("longitude")val longitude: Double,
    @SerializedName("agent_id")val agentId: Int
)
