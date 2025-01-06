package com.fttx.partner.data.source.remote.location

import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.domain.model.Agents
import com.fttx.partner.domain.model.LocationUpdateRequestBody
import com.fttx.partner.domain.model.Login

interface ILocationRemoteDataSource {
    suspend fun updateLocation(locationUpdateRequestBody: LocationUpdateRequestBody): NetworkResultWrapper<Unit>
}