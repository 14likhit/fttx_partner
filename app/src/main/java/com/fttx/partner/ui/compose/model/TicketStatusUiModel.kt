package com.fttx.partner.ui.compose.model

import androidx.compose.ui.graphics.Color
import com.fttx.partner.ui.compose.theme.Green10
import com.fttx.partner.ui.compose.theme.Green50
import com.fttx.partner.ui.compose.theme.Red10
import com.fttx.partner.ui.compose.theme.Red50
import com.fttx.partner.ui.compose.theme.Teal10
import com.fttx.partner.ui.compose.theme.Teal50
import com.fttx.partner.ui.compose.theme.YellowAmber10
import com.fttx.partner.ui.compose.theme.YellowAmber50

enum class TicketStatusUiModel(
    val status: String,
    val order: Int,
    val backgroundColor: Color,
    val textColor: Color
) {
    Assigned("Assigned", 1, Teal10, Teal50),
    InProgress("Inprogress", 2, YellowAmber10, YellowAmber50),
    Complete("Complete", 3, Green10, Green50),
    Invalid("Invalid", 4, Red10, Red50);

    companion object {
        fun fromStatus(status: String): TicketStatusUiModel {
            return entries.find { it.status == status } ?: Assigned
        }
    }
}
