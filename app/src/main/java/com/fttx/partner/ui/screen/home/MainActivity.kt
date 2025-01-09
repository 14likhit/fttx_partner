package com.fttx.partner.ui.screen.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.fttx.partner.data.source.local.datastore.DataStorePreferences
import com.fttx.partner.ui.compose.theme.FTTXPartnerTheme
import com.fttx.partner.ui.screen.account.AccountActivity
import com.fttx.partner.ui.screen.form.TicketFormActivity
import com.fttx.partner.ui.utils.Constants.BundleKey.CUSTOMER
import com.fttx.partner.ui.utils.Constants.BundleKey.TICKET
import com.fttx.partner.ui.utils.Constants.BundleKey.USER
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    @Inject lateinit var dataStorePreferences: DataStorePreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            firebaseAnalytics = Firebase.analytics
            firebaseAnalytics.logEvent("user_main_screen"){
                param("name", dataStorePreferences.getUserName())
            }
        }
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
                            startActivity(Intent(this, AccountActivity::class.java).apply {
                                putExtra(USER,it)
                            })
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