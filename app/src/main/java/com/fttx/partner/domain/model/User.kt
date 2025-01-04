package com.fttx.partner.domain.model

import android.os.Parcelable
import com.fttx.partner.ui.compose.model.UserUiModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val userId: Int,
    val name: String,
    val mobile: String,
    val email: String,
    val isAdmin: Boolean,
) : Parcelable {
    fun toUserUiModel(): UserUiModel {
        return UserUiModel(
            userId = userId,
            name = name,
            mobile = mobile,
            email = email,
        )
    }
}