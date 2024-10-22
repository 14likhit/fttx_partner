package com.fttx.partner.ui.screen.home

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.fttx.partner.domain.model.Customer
import com.fttx.partner.domain.model.Ticket
import com.fttx.partner.ui.compose.theme.Caption01Bold
import com.fttx.partner.ui.compose.theme.Caption01Regular
import com.fttx.partner.ui.utils.location.RequestLocationPermission
import com.fttx.partner.ui.utils.location.areLocationPermissionsGranted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun HomeRoute(
    navigateToTicketFormActivity: (Ticket?, Customer?) -> Unit,
    navigateToAccountActivity: () -> Unit,
    navigateToCallerActivity: (String) -> Unit,
    onBackPress: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by homeViewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    val isPermissionAsked = remember { mutableStateOf(areLocationPermissionsGranted(context)) }
    val isPermissionRevoked = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        homeViewModel.uiEffect.onEach {
            when (it) {
                HomeEffect.NavigateBack -> onBackPress()
                is HomeEffect.NavigateToAddTicket -> navigateToTicketFormActivity(null, it.customer)
                is HomeEffect.NavigateToTicketDetails -> navigateToTicketFormActivity(
                    it.ticket,
                    null
                )

                HomeEffect.NavigateToAccount -> navigateToAccountActivity()
                is HomeEffect.NavigateToCall -> navigateToCallerActivity(it.ticket.customer.phone)
                HomeEffect.NavigateToLocationPermissionRequiredPopUp -> {
                    isPermissionAsked.value = true
                }

                HomeEffect.NavigateToLocationPermissionRequiredSettingsPopUp -> {
                    isPermissionRevoked.value = true
                }
            }
        }.collect()
    }

    when (uiState.locationPermissionState) {
        LocationPermissionState.LocationPermissionGranted -> {
            HomeScreen(
                onTriggerIntent = {
                    coroutineScope.launch {
                        homeViewModel.intents.send(it)
                    }
                },
                uiState = uiState
            )
        }

        LocationPermissionState.LocationPermissionDenied,
        LocationPermissionState.LocationPermissionRevoked -> {
            LocationScreen(
                onTriggerIntent = {
                    coroutineScope.launch {
                        homeViewModel.intents.send(it)
                    }
                },
            )
        }
    }

    if (isPermissionAsked.value) {
        RequestLocationPermission(
            onPermissionGranted = {
                coroutineScope.launch {
                    homeViewModel.intents.send(HomeIntent.LocationPermissionGranted)
                }
                isPermissionAsked.value = false
            },
            onPermissionDenied = {
                coroutineScope.launch {
                    homeViewModel.intents.send(HomeIntent.LocationPermissionDenied)
                }
                isPermissionAsked.value = false
            },
            onPermissionsRevoked = {
                coroutineScope.launch {
                    homeViewModel.intents.send(HomeIntent.LocationPermissionRevoked)
                }
                isPermissionAsked.value = false
            })
    }

    if (isPermissionRevoked.value) {
        AlertDialog(
            text = {
                Text(text = "Location Permission needed to use app")
            },
            onDismissRequest = {
                isPermissionRevoked.value = true
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        isPermissionRevoked.value = false
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", context.packageName, null)
                        )
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(context, intent, null)
                    }) {
                    Text("Grant permission from setting", style = Caption01Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        isPermissionRevoked.value = true
                    }) {
                    Text("Cancel", style = Caption01Regular)
                }
            })
    }
}