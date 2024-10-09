package com.fttx.partner.ui.screen.form

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.fttx.partner.domain.model.Customer
import com.fttx.partner.domain.model.Ticket
import com.fttx.partner.ui.compose.theme.FTTXPartnerTheme
import com.fttx.partner.ui.utils.Constants.BundleKey.CUSTOMER
import com.fttx.partner.ui.utils.Constants.BundleKey.TICKET
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketFormActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val ticket: Ticket? = intent.extras?.getParcelable(TICKET)
        val customer: Customer? = intent.extras?.getParcelable(CUSTOMER)
        setContent {
            FTTXPartnerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TicketFormRoute(
                        ticket = ticket,
                        customer = customer,
                        onBackPress = {
                            finish()
                        })
                }
            }
        }
    }
}


