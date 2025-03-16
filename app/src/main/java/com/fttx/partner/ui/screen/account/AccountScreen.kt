package com.fttx.partner.ui.screen.account

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fttx.partner.R
import com.fttx.partner.domain.model.User
import com.fttx.partner.ui.compose.component.toolbar.FTTXTopAppBar
import com.fttx.partner.ui.compose.theme.Caption01Regular
import com.fttx.partner.ui.compose.theme.CoolGray05
import com.fttx.partner.ui.compose.theme.CoolGray50
import com.fttx.partner.ui.utils.NavigationIcon

@Composable
fun AccountScreen(
    user: User,
    onTriggerIntent: (AccountIntent) -> Unit,
    uiState: AccountState,
    modifier: Modifier = Modifier
) {

    Surface(color = Color.White, modifier = modifier.fillMaxSize()) {
        Column {
            FTTXTopAppBar(
                title = "Account",
                backIcon = {
                    NavigationIcon(onBackClick = {
                        onTriggerIntent(AccountIntent.BackCta)
                    })
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = WindowInsets.navigationBars
                            .asPaddingValues()
                            .calculateBottomPadding()
                    ),
            ) {
                // Profile section
                ProfileSection(user)

                Spacer(modifier = Modifier.height(24.dp))

                // Settings section
                // SettingsSection()

                // Logout button
                LogoutButton(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally), onLogout = {
                        onTriggerIntent(AccountIntent.LogoutCta)
                    })
            }
        }
    }
}

@Composable
fun ProfileSection(user: User) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .fillMaxSize()
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        MenuItem("Name:", user.name)
        MenuItem("Mobile:", user.mobile)
        MenuItem("Email:", user.email)
    }
}

@Composable
fun MenuItem(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 4.dp)) {
        Text(
            text = title,
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
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

@Composable
fun SettingsSection() {
    Column {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        // Add your settings items here
        SettingsItem("Notifications")
        SettingsItem("Privacy")
        SettingsItem("Security")
    }
}

@Composable
fun SettingsItem(title: String) {
    TextButton(
        onClick = { /* Handle click */ },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = title)
    }
}

@Composable
fun LogoutButton(onLogout: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = { onLogout() },
        modifier = modifier
    ) {
        Text("Logout")
    }
}

@Preview(showBackground = true)
@Composable
fun AccountScreenPreview() {
    MaterialTheme {
        AccountScreen(
            user = User(-1, "John Doe", "", "", false, false, false),
            onTriggerIntent = {}, uiState = AccountState()
        )
    }
}