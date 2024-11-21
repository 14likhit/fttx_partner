package com.fttx.partner.ui.screen.login

data class LoginUiModel(
    var login: String = "",
    var pwd: String = "",
    var remember: Boolean = false
) {
    fun isNotBlank(): Boolean {
        return login.isNotBlank() && pwd.isNotBlank()
    }
}
