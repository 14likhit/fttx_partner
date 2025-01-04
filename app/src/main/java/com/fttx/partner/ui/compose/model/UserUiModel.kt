package com.fttx.partner.ui.compose.model

data class UserUiModel(
    val userId: Int,
    val name: String,
    val mobile: String,
    val email: String,
    var isSelected: Boolean = false,
)
