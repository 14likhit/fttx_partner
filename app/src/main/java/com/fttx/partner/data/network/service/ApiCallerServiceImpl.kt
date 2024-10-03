package com.fttx.partner.data.network.service

import com.fttx.partner.data.network.util.NetworkException
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class ApiCallerServiceImpl
@Inject
constructor(
) : ApiCallerService {
    override suspend fun <T> safeApiCall(
        apiCallBlock: suspend () -> Response<T>,
    ): Result<T> {
        return try {
            val response = apiCallBlock.invoke()
            return if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string().toString()))
            }
        } catch (networkException: NetworkException) {
            Result.failure(networkException)
        } catch (throwable: CancellationException) {
            Result.failure(throwable)
        } catch (throwable: Throwable) {
            Result.failure(throwable)
        }
    }
}
