package com.fttx.partner.data.network.util

data class CustomExceptionEntity(
    var requestId: String? = null,
    var clientRequestId: String? = null,
    var source: String? = null,
    var errorCode: Int? = null,
    var httpErrorCode: Int? = null,
    var errorMessage: String? = null,
    var endPoint: String? = null,
    var failureStatus: String? = null,
)
