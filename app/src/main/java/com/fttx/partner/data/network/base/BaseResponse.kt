package com.fttx.partner.data.network.base

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BaseResponse<T>(
    @SerializedName("data") val data: T?,
    @SerializedName("error") val error: BaseErrorResponse?,
) : Serializable
