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
import com.fttx.partner.ui.component.ticket.TicketList
import com.fttx.partner.ui.mock.getTickets
import com.fttx.partner.ui.theme.FTTXPartnerTheme
import com.fttx.partner.ui.utils.NavigationIcon
import com.fttx.partner.ui.utils.displayText
import com.fttx.partner.ui.utils.getWeekPageTitle
import com.fttx.partner.ui.utils.rememberFirstVisibleWeekAfterScroll
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onTriggerIntent: (HomeIntent) -> Unit,
    uiState: HomeState,
    modifier: Modifier = Modifier
) {
    val currentDate = remember { LocalDate.now() }
    val startDate = remember { currentDate.minusDays(500) }
    val endDate = remember { currentDate.plusDays(500) }
    var selection by remember { mutableStateOf(currentDate) }
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        ) {
            val state = rememberWeekCalendarState(
                startDate = startDate,
                endDate = endDate,
                firstVisibleWeekDate = currentDate,
            )
            val visibleWeek = rememberFirstVisibleWeekAfterScroll(state)
            TopAppBar(
                title = { Text(text = getWeekPageTitle(visibleWeek)) },
                navigationIcon = {
                    NavigationIcon(onBackClick = {
                        onTriggerIntent(HomeIntent.BackCta)
                    })
                },
                actions = {
                    Icon(
                        imageVector = Icons.Rounded.AccountCircle,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                onTriggerIntent(HomeIntent.AccountCta)
                            })
                },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
            //todo -> if we have to use calendar need to evaluate calender crash.
//            Column {
//                WeekCalendar(
//                    modifier = Modifier.background(color = colorResource(R.color.purple_700)),
//                    state = state,
//                    dayContent = { day ->
//                        Day(day.date, isSelected = selection == day.date) { clicked ->
//                            if (selection != clicked) {
//                                selection = clicked
//                            }
//                        }
//                    },
//                )
//                TicketList(
//                    tickets = getTickets(),
//                    onCardClick = onCardClick
//                )
//            }
            TicketList(
                tickets = getTickets(),
                onCardClick = {
                    onTriggerIntent(HomeIntent.TicketCardCta(it))
                },
            )
        }
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

private val dateFormatter = DateTimeFormatter.ofPattern("dd")

@Composable
private fun Day(date: LocalDate, isSelected: Boolean, onClick: (LocalDate) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick(date) },
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = date.dayOfWeek.displayText(),
                fontSize = 12.sp,
                color = Color.White,
                fontWeight = FontWeight.Light,
            )
            Text(
                text = dateFormatter.format(date),
                fontSize = 14.sp,
                color = if (isSelected) colorResource(R.color.teal_200) else Color.White,
                fontWeight = FontWeight.Bold,
            )
        }
        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(colorResource(R.color.teal_200))
                    .align(Alignment.BottomCenter),
            )
        }
    }
}

@Preview
@Composable
private fun CalenderScreenPreview() {
    FTTXPartnerTheme {
        HomeScreen({}, HomeState())
    }
}
