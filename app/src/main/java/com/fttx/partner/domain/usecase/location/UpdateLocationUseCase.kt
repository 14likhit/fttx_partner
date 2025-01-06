package com.fttx.partner.domain.usecase.location

import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.data.network.util.SemaaiResult
import com.fttx.partner.domain.model.LocationUpdateRequestBody
import com.fttx.partner.domain.repository.location.ILocationRepository
import com.fttx.partner.domain.util.coroutine.CoroutineDispatcherProvider
import com.fttx.partner.domain.util.coroutine.UiText
import com.fttx.partner.domain.util.coroutine.executeSafeCall
import javax.inject.Inject

class UpdateLocationUseCase @Inject constructor(
    private val locationRepository: ILocationRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) {

    suspend operator fun invoke(
        agentId: Int, location: Pair<Double, Double>
    ): SemaaiResult<Unit, UiText> =
        coroutineDispatcherProvider.switchToIO {
            executeSafeCall(
                block = {
                    when (val result =
                        locationRepository.updateLocation(
                            LocationUpdateRequestBody(
                                location.first,
                                location.second,
                                agentId,
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