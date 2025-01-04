package com.fttx.partner.ui.mock

import com.fttx.partner.ui.compose.model.UserUiModel

fun getUsers(): List<UserUiModel> = List(10) {
    getUser()
}

fun getUser() = UserUiModel(
    userId = 1,
    name = "name",
    mobile = "+919405941144",
    email = "email",
    isSelected = false,
)