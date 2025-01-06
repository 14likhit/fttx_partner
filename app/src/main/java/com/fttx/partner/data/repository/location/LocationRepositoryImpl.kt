package com.fttx.partner.data.repository.location

import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.data.source.remote.agents.IAgentRemoteDataSource
import com.fttx.partner.data.source.remote.location.ILocationRemoteDataSource
import com.fttx.partner.domain.model.Agents
import com.fttx.partner.domain.model.LocationUpdateRequestBody
import com.fttx.partner.domain.repository.agents.IAgentRepository
import com.fttx.partner.domain.repository.location.ILocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationRemoteDataSource: ILocationRemoteDataSource
) : ILocationRepository {

    override suspend fun updateLocation(locationUpdateRequestBody: LocationUpdateRequestBody): NetworkResultWrapper<Unit> {
        return locationRemoteDataSource.updateLocation(locationUpdateRequestBody)
    }
}