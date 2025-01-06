package com.fttx.partner.ui.screen.form

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fttx.partner.R
import com.fttx.partner.domain.model.Customer
import com.fttx.partner.domain.model.Ticket
import com.fttx.partner.ui.compose.component.toolbar.FTTXTopAppBar
import com.fttx.partner.ui.compose.model.TicketPriorityUiModel
import com.fttx.partner.ui.compose.model.TicketStatusUiModel
import com.fttx.partner.ui.compose.model.UserUiModel
import com.fttx.partner.ui.compose.theme.Caption01Regular
import com.fttx.partner.ui.compose.theme.CoolGray05
import com.fttx.partner.ui.compose.theme.CoolGray50
import com.fttx.partner.ui.compose.theme.FTTXPartnerTheme
import com.fttx.partner.ui.compose.theme.Grape70
import com.fttx.partner.ui.compose.theme.Subheading01Bold
import com.fttx.partner.ui.compose.theme.Text01Bold
import com.fttx.partner.ui.mock.getCustomer
import com.fttx.partner.ui.mock.getTicket
import com.fttx.partner.ui.mock.getUsers
import com.fttx.partner.ui.utils.NavigationIcon
import com.fttx.partner.ui.utils.clickable
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun TicketFormScreen(
    ticket: Ticket?,
    customer: Customer?,
    onTriggerIntent: (TicketFormIntent) -> Unit,
    uiState: TicketFormState,
    modifier: Modifier = Modifier
) {
    val maxLength = 10
    var status by rememberSaveable {
        mutableStateOf(
            ticket?.status ?: TicketStatusUiModel.Assigned.status
        )
    }
    var priority by rememberSaveable { mutableStateOf("") }
    var endDate by rememberSaveable { mutableLongStateOf(0L) }
    Surface(color = Color.White, modifier = modifier.fillMaxSize()) {
        Column {
            FTTXTopAppBar(
                title = ticket?.let { stringResource(R.string.update_ticket) } ?: stringResource(
                    R.string.add_ticket
                ),
                backIcon = {
                    NavigationIcon(onBackClick = { onTriggerIntent(TicketFormIntent.BackCta) })
                }
            )
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp,
                        bottom = WindowInsets.navigationBars
                            .asPaddingValues()
                            .calculateBottomPadding()
                    ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Spacer(modifier = Modifier.padding(8.dp))
                ticket?.let {
                    Row {
                        Text(text = ticket.id, style = Text01Bold, modifier = Modifier.weight(1f))
                        Text(
                            text = ticket.ticketType,
                            style = Subheading01Bold,
                            color = Grape70,
                        )
                    }
                }
                TicketTitle(ticket = ticket)
                TicketStatus(ticket = ticket, dropDownSelection = { status = it })
                TicketDescription(ticket = ticket)
                ticket?.let {
                    TicketCustomerDetail(it)
                } ?: run {
                    customer?.let {
                        TicketCustomerDetail(null)
                    }
                }
                TicketPriority(
                    ticket = ticket,
                    dropDownSelection = { priority = it },
                    modifier = Modifier.fillMaxWidth(),
                )
                AssociateAgents(
                    selectedAgents = uiState.selectedAgents,
                    onClick = { onTriggerIntent(TicketFormIntent.NavigateToAgentBottomSheet) },
                    modifier = Modifier.fillMaxWidth()
                )
                EstimatedEndDateCompletion(
                    ticket = ticket,
                    { endDate = it }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                    enabled = if (ticket != null) {
                        ticket.status != status || uiState.selectedAgents.isNotEmpty()
                    } else {
                        true
                    },
                    onClick = {
                        onTriggerIntent(
                            TicketFormIntent.UpdateTicketCta(
                                ticket?.id?.toInt() ?: -1,
                                status
                            )
                        )
                    }) {
                    Text(text = ticket?.let { stringResource(R.string.update_ticket) }
                        ?: stringResource(
                            R.string.add_ticket
                        ))
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        onTriggerIntent(TicketFormIntent.Init)
    }
}

@Composable
private fun TicketTitle(ticket: Ticket?, modifier: Modifier = Modifier) {
    var title by rememberSaveable { mutableStateOf(ticket?.title ?: "") }
    var isNameError by rememberSaveable { mutableStateOf(false) }
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.title),
            style = Caption01Regular.copy(color = CoolGray50)
        )
        TextField(
            value = title,
            enabled = ticket == null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
                .border(2.dp, color = CoolGray05, shape = RoundedCornerShape(8.dp)),
            onValueChange = {
                if (it.length > 10) {
                    isNameError = true
                } else {
                    title = it
                    isNameError = false
                }
            },
            trailingIcon = {
                if (isNameError) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "Error",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.repair_new_connection),
                    color = CoolGray50
                )
            },
            isError = isNameError,
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                errorContainerColor = Color.White,
                disabledTextColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )
        if (isNameError) {
            Text(text = "Error Name")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TicketStatus(
    ticket: Ticket?,
    dropDownSelection: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val currentStatus = TicketStatusUiModel.fromStatus(ticket?.status ?: "")
        val statuses = TicketStatusUiModel.entries.filter { it.order > currentStatus.order }
        var expanded by remember { mutableStateOf(false) }
        var selectedStatus by remember { mutableStateOf(currentStatus) }
        Text(
            text = stringResource(R.string.status),
            style = Caption01Regular.copy(color = CoolGray50)
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            onExpandedChange = { expanded = !expanded }) {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .border(2.dp, color = CoolGray05, shape = RoundedCornerShape(8.dp)),
                readOnly = true,
                value = selectedStatus.status,
                onValueChange = {},
                trailingIcon = {
                    if ((currentStatus != TicketStatusUiModel.Complete) && (currentStatus != TicketStatusUiModel.Invalid)) {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    }
                },
                colors = TextFieldDefaults.colors().copy(
                    focusedTextColor = selectedStatus.textColor,
                    unfocusedTextColor = selectedStatus.textColor,
                    disabledTextColor = selectedStatus.textColor,
                    errorTextColor = selectedStatus.textColor,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                )
            )
            if ((currentStatus != TicketStatusUiModel.Complete) && (currentStatus != TicketStatusUiModel.Invalid)) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(color = Color.White)
                ) {
                    statuses.forEach { status ->
                        DropdownMenuItem(
                            text = { Text(text = status.status) },
                            colors = MenuDefaults.itemColors().copy(
                                textColor = status.textColor,
                            ),
                            onClick = {
                                selectedStatus = status
                                dropDownSelection(selectedStatus.status)
                                expanded = false
                            })
                    }
                }
            }
        }
    }
}

@Composable
fun TicketDescription(ticket: Ticket?, modifier: Modifier = Modifier) {
    if (ticket != null && ticket.description.isEmpty()) return
    var description by rememberSaveable { mutableStateOf(ticket?.description ?: "") }
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.description),
            style = Caption01Regular.copy(color = CoolGray50)
        )
        TextField(
            value = description,
            enabled = ticket == null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
                .border(2.dp, color = CoolGray05, shape = RoundedCornerShape(8.dp)),
            onValueChange = { description = it },
            placeholder = {
                Text(
                    text = stringResource(R.string.description),
                    color = if (ticket != null) Color.Black else CoolGray50
                )
            },
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                errorContainerColor = Color.White,
                disabledTextColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
        )
    }
}

@Composable
private fun TicketCustomerDetail(ticket: Ticket?, modifier: Modifier = Modifier) {
    if (ticket != null && ticket.customerName.isEmpty()) return
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.customer_details),
            style = Caption01Regular.copy(color = CoolGray50)
        )
        OutlinedCard(
            colors = CardDefaults.cardColors().copy(
                containerColor = Color.White,
            ),
            border = BorderStroke(
                width = 2.dp,
                color = CoolGray05
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(text = ticket?.customerName ?: "")
                Text(text = ticket?.customerAddress ?: "")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TicketPriority(
    ticket: Ticket?,
    dropDownSelection: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier) {
        Text(
            text = stringResource(R.string.priority),
            style = Caption01Regular.copy(color = CoolGray50)
        )
        val currentPriority = TicketPriorityUiModel.fromPriority(ticket?.priority ?: "")
        val priorities = TicketPriorityUiModel.entries
        var expanded by remember { mutableStateOf(false) }
        var selectedPriority by remember { mutableStateOf(currentPriority) }
        ExposedDropdownMenuBox(
            expanded = expanded,
            modifier = Modifier.background(color = Color.White),
            onExpandedChange = { expanded = !expanded }) {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .border(2.dp, color = CoolGray05, shape = RoundedCornerShape(8.dp)),
                readOnly = true,
                value = selectedPriority.priority,
                onValueChange = {},
                leadingIcon = {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(selectedPriority.backgroundColor),
                    )
                },
                trailingIcon = {
                    if (ticket == null) {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    }
                },
                colors = TextFieldDefaults.colors().copy(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                )
            )
            if (ticket == null) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    modifier = Modifier.background(color = Color.White),
                    onDismissRequest = { expanded = false }) {
                    priorities.forEach { priority ->
                        DropdownMenuItem(
                            text = { Text(text = priority.priority) },
                            leadingIcon = {
                                Box(
                                    modifier = Modifier
                                        .background(priority.backgroundColor)
                                        .size(8.dp)
                                )
                            },
                            onClick = {
                                selectedPriority = priority
                                dropDownSelection(selectedPriority.priority)
                                expanded = false
                            })
                    }
                }
            }
        }
    }
}

@Composable
private fun EstimatedEndDateCompletion(
    ticket: Ticket?,
    endDate: (Long) -> Unit, modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.estimated_completion_date),
            style = Caption01Regular.copy(color = CoolGray50)
        )
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed: Boolean by interactionSource.collectIsPressedAsState()
        val currentDate = Date().toFormattedString()
        var selectedDate by rememberSaveable { mutableStateOf(currentDate) }

        val context = LocalContext.current
        val calender = Calendar.getInstance()
        val year: Int = calender.get(Calendar.YEAR)
        val month: Int = calender.get(Calendar.MONTH)
        val day: Int = calender.get(Calendar.DAY_OF_MONTH)
        calender.time = Date()

        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, seletectedYear: Int, seletectedMonth: Int, selectedDayOfMonth: Int ->
                val newDate = Calendar.getInstance()
                newDate.set(seletectedYear, seletectedMonth, selectedDayOfMonth)
                selectedDate =
                    "${seletectedMonth.toMonthName()} $selectedDayOfMonth, $seletectedYear"
                endDate(newDate.timeInMillis)
            },
            year,
            month,
            day
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
                .border(2.dp, color = CoolGray05, shape = RoundedCornerShape(8.dp)),
            readOnly = true,
            value = selectedDate,
            onValueChange = {},
            trailingIcon = { Icons.Default.DateRange },
            interactionSource = if (ticket == null) interactionSource else null,
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            )
        )

        if (isPressed) {
            datePickerDialog.show()
        }
    }
}

@Composable
private fun AssociateAgents(
    selectedAgents: List<UserUiModel>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.clickable { onClick() }) {
        Text(
            text = stringResource(R.string.associates),
            style = Caption01Regular.copy(color = CoolGray50)
        )
        OutlinedCard(
            colors = CardDefaults.cardColors().copy(
                containerColor = Color.White,
            ),
            border = BorderStroke(
                width = 2.dp,
                color = CoolGray05
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                selectedAgents.forEach {
                    Text(text = it.name)
                }
                if(selectedAgents.isEmpty()) {
                    Text(text = stringResource(R.string.select_associate))
                }
            }
        }
    }
}

private fun Int.toMonthName(): String {
    return DateFormatSymbols().months[this]
}

private fun Date.toFormattedString(): String {
    val simpleDateFormat = SimpleDateFormat("LLLL dd, yyyy", Locale.getDefault())
    return simpleDateFormat.format(this)
}

@Preview
@Composable
private fun AddTicketFormScreenPreview(modifier: Modifier = Modifier) {
    FTTXPartnerTheme {
        TicketFormScreen(
            getTicket(),
            getCustomer(),
            { },
            TicketFormState(selectedAgents = getUsers()),
            modifier
        )
    }
}