package com.fttx.partner.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Agents(
    val status: String,
    val agents: List<User>,
): Parcelable