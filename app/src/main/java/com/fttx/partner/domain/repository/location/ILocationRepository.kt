package com.fttx.partner.domain.repository.location

import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.domain.model.LocationUpdateRequestBody

interface ILocationRepository {
    suspend fun updateLocation(locationUpdateRequestBody: LocationUpdateRequestBody): NetworkResultWrapper<Unit>
}