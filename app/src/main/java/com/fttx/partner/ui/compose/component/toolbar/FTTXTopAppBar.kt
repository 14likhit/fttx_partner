package com.fttx.partner.ui.compose.component.toolbar

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fttx.partner.ui.compose.theme.FTTXPartnerTheme
import com.fttx.partner.ui.compose.theme.ToolbarTitle
import com.fttx.partner.ui.compose.utils.DevicePreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FTTXTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    backIcon: (@Composable () -> Unit)? = null,
    action: (@Composable RowScope.() -> Unit)? = null,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = ToolbarTitle
            )
        },
        navigationIcon = backIcon ?: {},
        actions = action ?: {},
        modifier = modifier,
    )
}

@SuppressLint("ComposePreviewPublic")
@DevicePreviews
@Composable
fun FTTXTopAppBarPreviewWithBack() {
    FTTXPartnerTheme {
        FTTXTopAppBar(
            title = "Title",
            backIcon = {

            },
        )
    }
}

@SuppressLint("ComposePreviewPublic")
@DevicePreviews
@Composable
fun FTTXTopAppBarPreview() {
    FTTXPartnerTheme {
        FTTXTopAppBar(title = "Title")
    }
}
