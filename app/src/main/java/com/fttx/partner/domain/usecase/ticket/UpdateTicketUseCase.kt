package com.fttx.partner.domain.usecase.ticket

import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.data.network.util.SemaaiResult
import com.fttx.partner.domain.model.TicketUpdate
import com.fttx.partner.domain.model.TicketUpdateRequestBody
import com.fttx.partner.domain.repository.ticket.ITicketRepository
import com.fttx.partner.domain.util.coroutine.CoroutineDispatcherProvider
import com.fttx.partner.domain.util.coroutine.UiText
import com.fttx.partner.domain.util.coroutine.executeSafeCall
import javax.inject.Inject

class UpdateTicketUseCase @Inject constructor(
    private val ticketRepository: ITicketRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) {

    suspend operator fun invoke(
        ticketId: Int, status: String, location: Pair<Double, Double>, selectedAgents: List<Int>
    ): SemaaiResult<TicketUpdate, UiText> =
        coroutineDispatcherProvider.switchToIO {
            executeSafeCall(
                block = {
                    when (val result =
                        ticketRepository.updateTicket(
                            TicketUpdateRequestBody(
                                ticketId,
                                status,
                                location.first,
                                location.second,
                                selectedAgents
                            )
                        )) {
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