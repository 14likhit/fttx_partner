package com.fttx.partner.ui.screen.home

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fttx.partner.ui.compose.component.ticket.TicketList
import com.fttx.partner.ui.compose.component.toolbar.FTTXTopAppBar
import com.fttx.partner.ui.compose.theme.FTTXPartnerTheme
import com.fttx.partner.ui.screen.backgroundlocation.LocationService

@Composable
fun HomeScreen(
    onTriggerIntent: (HomeIntent) -> Unit,
    uiState: HomeState,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        ) {
            FTTXTopAppBar(
                title = "Home",
                action = {
                    Icon(
                        imageVector = Icons.Rounded.AccountCircle,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                onTriggerIntent(HomeIntent.AccountCta)
                            })
                })
            if (uiState.isCheckedIn) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Mark Check Out", modifier = Modifier.weight(1f))
                    Button(onClick = {
                        val intent = Intent(context, LocationService::class.java).apply {
                            action = LocationService.ACTION_STOP_TRACKING
                        }
                        context.startService(intent)
                        onTriggerIntent(HomeIntent.CheckOut)
                    }) {
                        Text(text = "Check Out")
                    }
                }
            } else if(uiState.isCheckedOut) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Mark Check In", modifier = Modifier.weight(1f))
                    Button(onClick = {
                        val intent = Intent(context, LocationService::class.java).apply {
                            action = LocationService.ACTION_START_TRACKING
                        }
                        context.startForegroundService(intent)
                        onTriggerIntent(HomeIntent.CheckIn)
                    }) {
                        Text(text = "Check In")
                    }
                }
            }
            HorizontalDivider()
            TicketList(
                tickets = uiState.tickets,
                onCardClick = {
                    onTriggerIntent(HomeIntent.TicketCardCta(it))
                },
                onCallClick = {
                    onTriggerIntent(HomeIntent.PhoneCta(it))
                }
            )
        }
        if (uiState.user.isAdmin) {
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 64.dp),
                onClick = {
                    onTriggerIntent(HomeIntent.AddCta)
                }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "FAB")
            }
        }
        if (uiState.error.isNotBlank()) {
            Toast.makeText(context, uiState.error, Toast.LENGTH_SHORT).show()
            onTriggerIntent(HomeIntent.EmptyError)
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    FTTXPartnerTheme {
        HomeScreen({}, HomeState())
    }
}
