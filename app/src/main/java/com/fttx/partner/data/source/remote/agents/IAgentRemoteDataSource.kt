package com.fttx.partner.data.source.remote.agents

import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.domain.model.Agents
import com.fttx.partner.domain.model.Login

interface IAgentRemoteDataSource {
    suspend fun getAgents(userId: Int): NetworkResultWrapper<Agents>
}