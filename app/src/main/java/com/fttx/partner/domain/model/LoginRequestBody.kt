package com.fttx.partner.domain.model

import com.google.gson.annotations.SerializedName

data class LoginRequestBody(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("device_id")
    val deviceId: String
)
