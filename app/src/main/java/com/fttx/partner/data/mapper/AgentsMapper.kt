package com.fttx.partner.data.mapper

import com.fttx.partner.data.model.AgentsDto
import com.fttx.partner.domain.model.Agents

object AgentsMapper {

    fun mapAgentsDtoToAgents(agentsDto: AgentsDto): Agents {
        return Agents(
            status = agentsDto.status ?: "",
            agents = agentsDto.agents?.map { UserTicketMapper.mapUserDtoToUser(it) } ?: emptyList()
        )
    }
}