package com.fttx.partner.ui.screen.login

import com.fttx.partner.ui.mvicore.IEffect
import com.fttx.partner.ui.mvicore.IIntent
import com.fttx.partner.ui.mvicore.IState

sealed class LoginIntent : IIntent {
    data object Init : LoginIntent()
    data object BackCta : LoginIntent()
    data class LoginCta(val loginUiModel: LoginUiModel, val deviceId: String) : LoginIntent()
    data object EmptyError: LoginIntent()
}

sealed class LoginEffect : IEffect {
    data object NavigateBack : LoginEffect()
    data object NavigateToHome : LoginEffect()
}

data class LoginState(
    val id: Int = -1,
    val errorLogin: String = "",
    val isLoading: Boolean = true,
    val isLoggedIn: Boolean = false,
) : IState