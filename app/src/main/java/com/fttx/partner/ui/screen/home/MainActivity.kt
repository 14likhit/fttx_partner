package com.fttx.partner.ui.screen.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.fttx.partner.data.source.local.datastore.DataStorePreferences
import com.fttx.partner.ui.compose.theme.FTTXPartnerTheme
import com.fttx.partner.ui.screen.account.AccountActivity
import com.fttx.partner.ui.screen.backgroundlocation.LocationService
import com.fttx.partner.ui.screen.form.TicketFormActivity
import com.fttx.partner.ui.utils.Constants.BundleKey.CUSTOMER
import com.fttx.partner.ui.utils.Constants.BundleKey.TICKET
import com.fttx.partner.ui.utils.Constants.BundleKey.USER
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    @Inject
    lateinit var dataStorePreferences: DataStorePreferences


    private val trackingReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            when (intent.action) {
                LocationService.BROADCAST_TRACKING_STARTED -> {
                    // Handle tracking started
                    // Update UI, show notification, etc.
                    Log.e("MainActivity", "Tracking started")
                }

                LocationService.BROADCAST_TRACKING_STOPPED -> {
                    // Handle tracking stopped
                    // Update UI, hide notification, etc.
                    Log.e("MainActivity", "Tracking stopped")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            firebaseAnalytics = FirebaseAnalytics.getInstance(this@MainActivity)
            firebaseAnalytics.logEvent("user_main_screen") {
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
                                putExtra(USER, it)
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

    override fun onStart() {
        super.onStart()

        // Register for tracking broadcasts
        val intentFilter = IntentFilter().apply {
            addAction(LocationService.BROADCAST_TRACKING_STARTED)
            addAction(LocationService.BROADCAST_TRACKING_STOPPED)
        }
        registerReceiver(trackingReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()

        // Unregister the receiver when the activity is not visible
        unregisterReceiver(trackingReceiver)
    }
}