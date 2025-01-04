package com.fttx.partner.domain.repository.agents

import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.domain.model.Agents

interface IAgentRepository {
    suspend fun getAgents(userId: Int): NetworkResultWrapper<Agents>
}