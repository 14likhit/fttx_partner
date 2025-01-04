package com.fttx.partner.data.repository.agents

import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.data.source.remote.agents.IAgentRemoteDataSource
import com.fttx.partner.domain.model.Agents
import com.fttx.partner.domain.repository.agents.IAgentRepository
import javax.inject.Inject

class AgentRepositoryImpl @Inject constructor(
    private val agentRemoteDataSource: IAgentRemoteDataSource
) : IAgentRepository {
    override suspend fun getAgents(userId: Int): NetworkResultWrapper<Agents> {
        return agentRemoteDataSource.getAgents(userId)
    }
}