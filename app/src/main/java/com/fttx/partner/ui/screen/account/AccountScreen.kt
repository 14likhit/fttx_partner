package com.fttx.partner.ui.screen.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fttx.partner.R

@Composable
fun AccountScreen(
    onTriggerIntent: (AccountIntent) -> Unit,
    uiState: AccountState,
    modifier: Modifier = Modifier
) {

    Surface(color = Color.White, modifier = modifier.fillMaxSize()) {
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile section
            ProfileSection()

            Spacer(modifier = Modifier.height(24.dp))

            // Settings section
            // SettingsSection()

            // Logout button
            LogoutButton(modifier = Modifier.padding(16.dp), onLogout = {
                onTriggerIntent(AccountIntent.LogoutCta)
            })
        }
    }
}

@Composable
fun ProfileSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "John Doe",
            style = MaterialTheme.typography.headlineMedium
        )
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
        AccountScreen(onTriggerIntent = {}, uiState = AccountState())
    }
}