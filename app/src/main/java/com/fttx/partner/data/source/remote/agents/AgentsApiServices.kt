package com.fttx.partner.data.source.remote.agents

import com.fttx.partner.data.model.AgentsDto
import com.fttx.partner.data.network.util.EndPoints.Agents.GET_AGENTS
import com.fttx.partner.data.network.util.EndPoints.Agents.QUERY_AGENT_ID
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AgentsApiServices {

    @GET(GET_AGENTS)
    suspend fun getAgents(
        @Query(QUERY_AGENT_ID) userId: Int
    ): Response<AgentsDto>
}