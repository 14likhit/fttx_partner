package com.fttx.partner.data.source.remote.agents

import com.fttx.partner.data.mapper.AgentsMapper
import com.fttx.partner.data.network.service.ApiCallerService
import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.domain.model.Agents
import javax.inject.Inject

class AgentRemoteDataSourceImpl @Inject constructor(
    private val apiServices: AgentsApiServices,
    private val apiCallerService: ApiCallerService,
) : IAgentRemoteDataSource {

    override suspend fun getAgents(userId: Int): NetworkResultWrapper<Agents> {
        apiCallerService.safeApiCall {
            apiServices.getAgents(userId)
        }.onSuccess {
            return NetworkResultWrapper.Success(AgentsMapper.mapAgentsDtoToAgents(it))
        }.onFailure {
            return NetworkResultWrapper.Error()
        }
        return NetworkResultWrapper.Error()
    }
}