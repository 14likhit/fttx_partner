package com.fttx.partner.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val userId: Int,
    val name: String,
    val mobile: String,
    val email: String,
) : Parcelable
