package com.fttx.partner.ui.compose.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

fun Modifier.noRippleToggleable(
    value: Boolean,
    onValueChange: (Boolean) -> Unit,
    role: Role? = null,
): Modifier =
    composed {
        toggleable(
            value = value,
            indication = null,
            role = role,
            interactionSource = remember { MutableInteractionSource() },
            onValueChange = onValueChange,
        )
    }

fun Modifier.noRippleSelectable(
    selected: Boolean,
    enabled: Boolean = true,
    role: Role? = null,
    onClick: () -> Unit,
) = composed(
    inspectorInfo =
    debugInspectorInfo {
        name = "selectable"
        properties["selected"] = selected
        properties["enabled"] = enabled
        properties["role"] = role
        properties["onClick"] = onClick
    },
) {
    Modifier.selectable(
        selected = selected,
        enabled = enabled,
        role = role,
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick,
    )
}

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier =
    composed {
        clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
        ) {
            onClick()
        }
    }

class NoRippleInteractionSource : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}

    override fun tryEmit(interaction: Interaction) = true
}
