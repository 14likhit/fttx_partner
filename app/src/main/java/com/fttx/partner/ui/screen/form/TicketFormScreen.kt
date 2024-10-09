package com.fttx.partner.ui.screen.form

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fttx.partner.domain.model.Ticket
import com.fttx.partner.ui.theme.FTTXPartnerTheme
import com.fttx.partner.ui.utils.NavigationIcon
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketFormScreen(
    ticket: Ticket?,
    onTriggerIntent: (TicketFormIntent) -> Unit,
    uiState: TicketFormState,
    modifier: Modifier = Modifier
) {
    val maxLength = 10
    var title by rememberSaveable { mutableStateOf(ticket?.title?: "") }
    var isNameError by rememberSaveable { mutableStateOf(false) }
    var description by rememberSaveable { mutableStateOf("") }
    var priority by rememberSaveable { mutableStateOf("") }
    var endDate by rememberSaveable { mutableLongStateOf(0L) }

    Column {
        TopAppBar(
            title = { Text(text = ticket?.let { "Edit Ticket" } ?: "Add Ticket") },
            navigationIcon = { NavigationIcon(onBackClick = { onTriggerIntent(TicketFormIntent.BackCta) }) },
            colors = TopAppBarDefaults.topAppBarColors().copy(
                titleContentColor = Color.Black,
                navigationIconContentColor = Color.Black
            )
        )
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "Title")
            TextField(
                value = title,
                modifier = Modifier.fillMaxWidth(),
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
                placeholder = { Text(text = "Repair/New Connection") },
                isError = isNameError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            if (isNameError) {
                Text(text = "Error Name")
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "Description")
            TextField(
                value = description,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { description = it },
                placeholder = { Text(text = "Description") })
            DropDownSelector(
                dropDownSelection = { priority = it },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.padding(8.dp))
            EstimatedEndDateCompletion({ endDate = it }, modifier = Modifier.fillMaxWidth())
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownSelector(dropDownSelection: (String) -> Unit, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier) {
        Text(text = "Priority")
        val priorities = listOf("High", "Medium", "Low")
        var expanded by remember { mutableStateOf(false) }
        var selectedPriority by remember { mutableStateOf(priorities[0]) }
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                value = selectedPriority,
                onValueChange = {},
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                priorities.forEach { priority ->
                    DropdownMenuItem(
                        text = { Text(text = priority) },
                        onClick = {
                            selectedPriority = priority
                            dropDownSelection(selectedPriority)
                            expanded = false
                        })
                }
            }
        }
    }
}

@Composable
fun EstimatedEndDateCompletion(endDate: (Long) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = "Estimated Completion Date")
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
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            value = selectedDate,
            onValueChange = {},
            trailingIcon = { Icons.Default.DateRange },
            interactionSource = interactionSource
        )

        if (isPressed) {
            datePickerDialog.show()
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
        TicketFormScreen(null, { }, TicketFormState())
    }
}