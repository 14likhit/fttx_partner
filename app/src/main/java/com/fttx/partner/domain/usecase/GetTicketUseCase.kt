package com.fttx.partner.domain.usecase

import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.data.network.util.SemaaiResult
import com.fttx.partner.domain.repository.ITicketRepository
import com.fttx.partner.domain.util.coroutine.CoroutineDispatcherProvider
import com.fttx.partner.domain.util.coroutine.UiText
import com.fttx.partner.domain.util.coroutine.executeSafeCall
import javax.inject.Inject

class GetTicketUseCase @Inject constructor(
    private val ticketRepository: ITicketRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) {

    suspend operator fun invoke(): SemaaiResult<Any, UiText> =
        coroutineDispatcherProvider.switchToIO {
            executeSafeCall(
                block = {
                    when (val result = ticketRepository.getTicket()) {
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