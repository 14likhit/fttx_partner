package com.fttx.partner.ui.screen.employee

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fttx.partner.ui.compose.model.UserUiModel
import com.fttx.partner.ui.compose.theme.FTTXPartnerTheme
import com.fttx.partner.ui.compose.theme.Heading01Bold
import com.fttx.partner.ui.compose.theme.Subheading01Bold
import com.fttx.partner.ui.mock.getUsers
import com.fttx.partner.ui.utils.clickable

@Composable
fun EmployeeSelectionScreen(
    users: List<UserUiModel>,
    selectedUsers: List<UserUiModel>,
    onClick: (List<UserUiModel>) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            item {
                Text(
                    text = "Select Associate Agent",
                    style = Subheading01Bold,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .align(Alignment.Center)
                )
            }
            items(items = users) { employee ->
                var isSelected = remember { mutableStateOf(selectedUsers.contains(employee)) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isSelected.value = !isSelected.value }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isSelected.value,
                        onCheckedChange = { isSelected.value = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = employee.name,
                        modifier = Modifier.weight(1f)
                    )
                }
                employee.isSelected = isSelected.value
                HorizontalDivider()
            }
            item {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(top = 16.dp),
                    onClick = { onClick(users.filter { it.isSelected }) },
                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewEmployeeSelectionScreen(modifier: Modifier = Modifier) {
    FTTXPartnerTheme {
        EmployeeSelectionScreen(
            users = getUsers(),
            selectedUsers = listOf(),
            onClick = {},
            modifier = modifier
        )
    }
}