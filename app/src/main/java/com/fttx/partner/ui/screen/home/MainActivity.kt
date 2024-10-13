package com.fttx.partner.ui.screen.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.fttx.partner.ui.screen.account.AccountActivity
import com.fttx.partner.ui.screen.form.TicketFormActivity
import com.fttx.partner.ui.compose.theme.FTTXPartnerTheme
import com.fttx.partner.ui.utils.Constants.BundleKey.CUSTOMER
import com.fttx.partner.ui.utils.Constants.BundleKey.TICKET
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FTTXPartnerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeRoute(
                        navigateToTicketFormActivity = { ticket, customer ->
                            startActivity(Intent(this, TicketFormActivity::class.java).apply {
                                putExtra(TICKET, ticket)
                                putExtra(CUSTOMER, customer)
                            })
                        },
                        navigateToAccountActivity = {
                            startActivity(Intent(this, AccountActivity::class.java))
                        },
                        navigateToCallerActivity = {
                            try {
                                startActivity(
                                    Intent(
                                        Intent.ACTION_DIAL,
                                        Uri.parse("tel:$it")
                                    )
                                )
                            } catch (s: SecurityException) {
                                Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        },
                        onBackPress = { finish() },
                    )
                }
            }
        }
    }
}