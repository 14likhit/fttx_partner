package com.fttx.partner.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fttx.partner.R
import com.fttx.partner.ui.compose.component.ticket.TicketList
import com.fttx.partner.ui.compose.component.toolbar.FTTXTopAppBar
import com.fttx.partner.ui.mock.getTickets
import com.fttx.partner.ui.compose.theme.FTTXPartnerTheme
import com.fttx.partner.ui.utils.NavigationIcon
import com.fttx.partner.ui.utils.displayText
import com.fttx.partner.ui.utils.getWeekPageTitle
import com.fttx.partner.ui.utils.rememberFirstVisibleWeekAfterScroll
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(
    onTriggerIntent: (HomeIntent) -> Unit,
    uiState: HomeState,
    modifier: Modifier = Modifier
) {
    val currentDate = remember { LocalDate.now() }

    Box {
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
        if(uiState.user.isAdmin) {
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
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    FTTXPartnerTheme {
        HomeScreen({}, HomeState())
    }
}
