package com.fttx.partner.ui.screen.calender

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fttx.partner.ui.screen.form.TicketFormActivity
import com.fttx.partner.ui.theme.FTTXPartnerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FTTXPartnerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalenderScreen(
                        modifier = Modifier.padding(innerPadding),
                        close = { finish() },
                        onCardClick = {
                            startActivity(Intent(this, TicketFormActivity::class.java))
                        },
                        onAddClick = {
                            startActivity(Intent(this, TicketFormActivity::class.java))
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FTTXPartnerTheme {
        CalenderScreen()
    }
}