package com.fttx.partner.ui.screen.form

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.fttx.partner.domain.model.Ticket
import com.fttx.partner.ui.theme.FTTXPartnerTheme
import com.fttx.partner.ui.utils.Constants.BundleKey.SCREEN_TYPE
import com.fttx.partner.ui.utils.Constants.BundleKey.TICKET
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketFormActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val ticket : Ticket? = intent.extras?.getParcelable(TICKET)
        setContent {
            FTTXPartnerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TicketFormRoute(ticket = ticket, onBackPress = {
                        finish()
                    })
                }
            }
        }
    }
}


