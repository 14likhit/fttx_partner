package com.fttx.partner.data.network.util

import com.fttx.partner.data.network.base.BaseErrorResponse
import com.fttx.partner.data.network.util.ErrorHandlerConstants.ERROR_GENERIC
import com.fttx.partner.data.network.util.ErrorHandlerConstants.ERROR_NO_INTERNET
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import javax.net.ssl.HttpsURLConnection

/**
 * Handling API Throwable Extension function to handle Failure Error Response
 */
object ErrorHandler {
    fun Throwable.getErrorModel(
        genericErrorMessage: String = ErrorHandlerConstants.ERROR_MESSAGE,
        errorMessageParser: ((String) -> String)? = null,
    ): GenericErrorModel =
        try {
            when (this) {
                is HttpException -> {
                    val errorBody = response()?.errorBody()?.string()
                    if (response()?.code() != HttpsURLConnection.HTTP_UNAVAILABLE && !errorBody.isNullOrEmpty()) {
                        val errorMessageFromResponse =
                            if (errorMessageParser == null) {
                                val json = JSONObject(errorBody)
                                json[ErrorHandlerConstants.MESSAGE].toString()
                            } else {
                                errorMessageParser(errorBody)
                            }
                        GenericErrorModel(
                            errorMessageFromResponse,
                            errorMessageFromResponse,
                            responseCode =
                                response()?.code()
                                    ?: HTTP_ERROR_BAD_REQUEST,
                        )
                    } else {
                        GenericErrorModel(
                            this.message ?: genericErrorMessage,
                            genericErrorMessage,
                            responseCode =
                                response()?.code()
                                    ?: HTTP_ERROR_BAD_REQUEST,
                        )
                    }
                }

                is UnknownHostException ->
                    GenericErrorModel(
                        this.message ?: ErrorHandlerConstants.INTERNET_NOT_AVAILABLE,
                        ErrorHandlerConstants.INTERNET_NOT_AVAILABLE,
                        ERROR_NO_INTERNET,
                        responseCode = HTTP_ERROR_INTERNAL_SERVER,
                    )

                is NetworkException ->
                    GenericErrorModel(
                        this.customExceptionEntity?.errorMessage ?: ErrorHandlerConstants.ERROR_MESSAGE,
                        ErrorHandlerConstants.ERROR_MESSAGE,
                        ERROR_GENERIC,
                        responseCode = this.customExceptionEntity?.httpErrorCode ?: HTTP_ERROR_BAD_REQUEST,
                    )

                else ->
                    GenericErrorModel(
                        this.message ?: genericErrorMessage,
                        genericErrorMessage,
                        responseCode = HTTP_ERROR_INTERNAL_SERVER,
                    )
            }
        } catch (e: Exception) {
            GenericErrorModel(
                this.message ?: genericErrorMessage,
                genericErrorMessage,
                responseCode = HTTP_ERROR_INTERNAL_SERVER,
            )
        }

    fun parseErrorFromInfoModel(errorBody: String): String {
        return JSONObject(errorBody)[ErrorHandlerConstants.INFO].toString()
            .let {
                JSONObject(it)[ErrorHandlerConstants.MESSAGE].toString()
            }
    }

    fun parseErrorModelFromErrorResponse(errorBody: String?): BaseErrorResponse? {
        return if (errorBody != null) {
            try {
                Gson().fromJson(JSONObject(errorBody)[ErrorHandlerConstants.ERROR].toString(), BaseErrorResponse::class.java)
            } catch (e: JsonParseException) {
                null
            } catch (e: JsonSyntaxException) {
                null
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }
}

data class GenericErrorModel(
    val actualErrorMessage: String,
    val presentableErrorMessage: String,
    val errorType: Int = ERROR_GENERIC,
    val responseCode: Int = HTTP_ERROR_BAD_REQUEST,
)

class NetworkException(
    val customExceptionEntity: CustomExceptionEntity? = null,
) : IOException(customExceptionEntity?.errorMessage.orEmpty())
