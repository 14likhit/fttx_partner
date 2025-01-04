package com.fttx.partner.domain.usecase.agents

import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.data.network.util.SemaaiResult
import com.fttx.partner.domain.model.Agents
import com.fttx.partner.domain.repository.agents.IAgentRepository
import com.fttx.partner.domain.util.coroutine.CoroutineDispatcherProvider
import com.fttx.partner.domain.util.coroutine.UiText
import com.fttx.partner.domain.util.coroutine.executeSafeCall
import javax.inject.Inject


class GetAgentsUseCase @Inject constructor(
    private val agentRepository: IAgentRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) {

    suspend operator fun invoke(userId: Int): SemaaiResult<Agents, UiText> =
        coroutineDispatcherProvider.switchToIO {
            executeSafeCall(
                block = {
                    when (val result = agentRepository.getAgents(userId)) {
                        is NetworkResultWrapper.Error -> {
                            SemaaiResult.Error(UiText.DynamicString(result.message.orEmpty()))
                        }

                        is NetworkResultWrapper.Success -> {
                            SemaaiResult.Success(result.data)
                        }
                    }
                },
                error = {
                    SemaaiResult.Error(it)
                },
            )
        }

}