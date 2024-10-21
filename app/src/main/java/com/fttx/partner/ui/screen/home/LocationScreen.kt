package com.fttx.partner.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fttx.partner.ui.compose.theme.Caption01Bold
import com.fttx.partner.ui.compose.theme.Caption01Regular
import com.fttx.partner.ui.compose.theme.FTTXPartnerTheme

@Composable
fun LocationScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Icon (You can replace the painterResource with an actual icon from your resources)
            Icon(
                imageVector = Icons.Default.MyLocation,
                contentDescription = "Location Icon",
                modifier = Modifier.size(80.dp)
            )

            // Informative Text
            Text(
                text = "Location Permission Required",
                style = Caption01Bold,
                textAlign = TextAlign.Center
            )

            Text(
                text = "To use this app, we need access to your location. Please grant permission to continue.",
                style = Caption01Regular,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Primary Button to grant permission
            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Request Location Permission")
            }
        }
    }
}

@Preview
@Composable
private fun LocationScreenPreview() {
    FTTXPartnerTheme {
        LocationScreen()
    }
}