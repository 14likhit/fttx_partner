package com.fttx.partner.ui.screen.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.fttx.partner.domain.model.Ticket
import com.fttx.partner.ui.screen.account.AccountActivity
import com.fttx.partner.ui.screen.form.TicketFormActivity
import com.fttx.partner.ui.screen.form.TicketFormScreenType
import com.fttx.partner.ui.theme.FTTXPartnerTheme
import com.fttx.partner.ui.utils.Constants.BundleKey.SCREEN_TYPE
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
                        navigateToTicketFormActivity = {
                            startActivity(Intent(this, TicketFormActivity::class.java).apply {
                                putExtra(TICKET, it)
                            })
                        },
                        navigateToAccountActivity = {
                            startActivity(Intent(this, AccountActivity::class.java))
                        },
                        onBackPress = { finish() },
                    )
                }
            }
        }
    }
}