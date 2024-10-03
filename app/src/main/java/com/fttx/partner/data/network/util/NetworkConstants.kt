package com.fttx.partner.data.network.util

const val HTTP_RESPONSE_OK = 200
const val HTTP_RESPONSE_RESOURCE_CREATED = 201
const val HTTP_ERROR_BAD_REQUEST = 400
const val HTTP_ERROR_UNAUTHORIZED_REQUEST = 401
const val HTTP_ERROR_RESOURCE_FORBIDDEN = 403
const val HTTP_ERROR_RESOURCE_NOT_FOUND = 404
const val HTTP_ERROR_RESOURCE_ALREADY_EXIST = 409
const val HTTP_ERROR_PAYLOAD_TOO_LARGE = 413
const val HTTP_ERROR_UNABLE_TO_PROCESS_REQUEST = 422
const val HTTP_ERROR_INTERNAL_SERVER = 500
const val HTTP_REQUEST_UNSUPPORTED = 501
const val HTTP_ERROR_BAD_GATEWAY = 502
const val HTTP_ERROR_SERVER_MAINTENANCE = 503
const val HTTP_ERROR_MULTIPLE_RESPONSE = 300
const val DEFAULT_OFFSET = 0
const val POST_HEADERS_TYPE_TEXT = "Content-Type: application/text"
const val ERROR_INTERNET_CONNECTION = "Please connect to Internet"
const val POST_HEADERS_TYPE_PLAIN = "Content-Type: text/plain; charset=UTF-8"
const val HEADER_SOURCE_MARKETPLACE = "source: marketplace"

object ErrorHandlerConstants {
    const val MESSAGE = "message"
    const val CODE = "code"
    const val INFO = "info"
    const val ERROR = "error"
    const val ERROR_MESSAGE = "Ada yang salah. silakan coba lagi nanti" // Something went wrong, please try again.
    const val INTERNET_NOT_AVAILABLE = "Koneksi internet tidak ditemukan!" // "Please check your internet connection."
    const val UNABLE_TO_REACH_OUR_SERVER = "Unable to reach our servers."
    const val API_REQUEST_FAILED_WITH_CODE = "API request failed with code:"
    const val UNABLE_TO_REACH_SERVER =
        "Unable to reach server, please try again."
    const val ERROR_GENERIC = 0
    const val ERROR_NO_INTERNET = 1
    const val DATA_NO_FOUND = "No data found"
    const val NETWORK_INIT_LOG = "Network SDK - Init not called"
    const val NETWORK_TAG = "Network SDK"
    const val NETWORK_IGNORE_RE_INITIALISING_TAG = "Ignoring reinitialising of SDK"
    const val HTTP_CACHING_ENABLED = "Http Caching Enabled"
    const val KTOR_LOG = "Logger Ktor =>"
    const val HTTP_STATUS = "HTTP status"

    const val POST_REQUEST_BODY = "\"Network SDK - POST Request BODY can't be a function. Try passing a Serializable data object instead!"
}

object OkHttpConstants {
    const val DEFAULT_CONNECT_TIMEOUT_IN_SEC: Long = 90
    const val DEFAULT_WRITE_TIMEOUT_IN_SEC: Long = 90
    const val DEFAULT_READ_TIMEOUT_IN_SEC: Long = 90
    const val DEFAULT_CACHE_SIZE = (10 * 1024 * 1024).toLong() // 10 MB
}

object RequestHeaderConstants {
    const val AUTHORIZATION = "Authorization"
    const val CONTENT_TYPE = "Content-Type"
    const val TEXT_HTML = " text/plain; charset=UTF-8"
    const val SOCIAL_LOGIN = "SocialLogin"
    const val LOGIN = "Login"
    const val LANGUAGE = "lang"
    const val AUTH_TOKEN = "auth"
    const val VERSION_CODE = "versionCode"
    const val VERSION_NAME = "versionName"
    const val REQUEST_ID = "X-Request-Id"
    const val CACHE_CONTROL = "Cache-Control"
}

enum class ServerError(val value: String) {
    UNAVAILABLE_PRODUCTS(
        "The following products have less available stock than ordered!",
    ),
    INVALID_COUPON("Invalid Parameters! Seller Id"),
    PENDING_BNPL_BILLS("Please pay the outstanding BNPL bills first."),
    PENDING_ORDERS("There are ongoing orders , please wait for order completion"),
    ACCOUNT_DELETION_REQUESTED("The account with this number had sent a deletion request. Contact admin via WhatsApp for more information"),
    ACCOUNT_DELETED("The Account with this number had  been deleted. Contact admin via whatsapp for more information"),
    PREVIOUS_PAYMENT_IS_PENDING("One request of payment is not done yet for this seller, so please wait..."),
    REQUEST_PAYMENT_AFTER_X_DAYS("You can request another balance withdrawl after"),
}
