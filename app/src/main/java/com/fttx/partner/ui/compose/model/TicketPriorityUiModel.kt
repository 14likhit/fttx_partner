package com.fttx.partner.ui.compose.model

import androidx.compose.ui.graphics.Color
import com.fttx.partner.ui.compose.theme.Green50
import com.fttx.partner.ui.compose.theme.Red50
import com.fttx.partner.ui.compose.theme.YellowAmber50

enum class TicketPriorityUiModel(
    val priority: String,
    val backgroundColor: Color,
) {
    HIGH("High", Red50),
    MEDIUM("Medium", YellowAmber50),
    LOW("Low", Green50);

    companion object {
        fun fromPriority(priority: String): TicketPriorityUiModel {
            return entries.find { it.priority.equals(priority, ignoreCase = true) } ?: LOW
        }
    }
}
