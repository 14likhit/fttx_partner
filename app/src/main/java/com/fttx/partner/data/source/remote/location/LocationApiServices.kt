package com.fttx.partner.data.source.remote.location

import com.fttx.partner.data.network.util.EndPoints.Location.UPDATE_LOCATION_LOG
import com.fttx.partner.domain.model.LocationUpdateRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LocationApiServices {

    @POST(UPDATE_LOCATION_LOG)
    suspend fun updateLocationLog(
        @Body locationUpdateRequestBody: LocationUpdateRequestBody
    ): Response<Unit>
}