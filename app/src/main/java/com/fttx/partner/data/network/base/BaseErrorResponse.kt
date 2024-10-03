package com.fttx.partner.data.network.base

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BaseErrorResponse(
    @SerializedName("message") val message: String?,
    @SerializedName("e_type") val type: String?,
    @SerializedName("code") val code: Int?,
    @SerializedName("time") val time: Double?,
    @SerializedName("errors") val errors: List<String?>?,
) : Serializable
