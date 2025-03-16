package com.fttx.partner.ui.screen.home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.fttx.partner.domain.model.Customer
import com.fttx.partner.domain.model.Ticket
import com.fttx.partner.domain.model.User
import com.fttx.partner.ui.compose.theme.Caption01Bold
import com.fttx.partner.ui.compose.theme.Caption01Regular
import com.fttx.partner.ui.screen.backgroundlocation.LocationService
import com.fttx.partner.ui.screen.form.TicketFormActivity
import com.fttx.partner.ui.utils.Constants.BundleKey.TICKET
import com.fttx.partner.ui.utils.location.RequestLocationPermission
import com.fttx.partner.ui.utils.location.areLocationPermissionsGranted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun HomeRoute(
    mainViewModel: MainViewModel,
    navigateToTicketFormActivity: (Ticket?, Customer?) -> Unit,
    navigateToAccountActivity: (User) -> Unit,
    navigateToCallerActivity: (String) -> Unit,
    onBackPress: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by homeViewModel.uiState.collectAsState()
    val uiStateMainViewModel by mainViewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    val isPermissionAsked =
        remember { mutableStateOf(areLocationPermissionsGranted(context).not()) }
    val isPermissionRevoked = remember { mutableStateOf(false) }
    val shouldShowRationale = remember { mutableStateOf(true) }
    val showProgress = remember { mutableStateOf(false) }

    val ticketFormUpdate =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                coroutineScope.launch {
                    homeViewModel.intents.send(HomeIntent.Init(areLocationPermissionsGranted(context)))
                }
            }

        }

    LaunchedEffect(Unit) {
        homeViewModel.uiEffect.onEach {
            when (it) {
                HomeEffect.NavigateBack -> onBackPress()
                is HomeEffect.NavigateToAddTicket -> navigateToTicketFormActivity(null, it.customer)
                is HomeEffect.NavigateToTicketDetails -> {
                    ticketFormUpdate.launch(Intent(context, TicketFormActivity::class.java).apply {
                        putExtra(TICKET, it.ticket)
                    })
                }

                is HomeEffect.NavigateToAccount -> navigateToAccountActivity(it.user)
                is HomeEffect.NavigateToCall -> navigateToCallerActivity("+91${it.ticket.customerMobile}")
                HomeEffect.NavigateToLocationPermissionRequiredPopUp -> {
                    isPermissionAsked.value = true
                }

                HomeEffect.NavigateToLocationPermissionRequiredSettingsPopUp -> {
                    isPermissionRevoked.value = true
                }

                HomeEffect.ShowProgress -> {
                    showProgress.value = true
                }

                HomeEffect.AutoCheckout -> {
                    val intent = Intent(context, LocationService::class.java).apply {
                        action = LocationService.ACTION_STOP_TRACKING
                    }
                    context.startService(intent)
                    coroutineScope.launch {
                        homeViewModel.intents.send(HomeIntent.CheckOut)
                    }
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
            if (uiState.isLoading) {
                Dialog(
                    onDismissRequest = { },
                    DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }

        LocationPermissionState.LocationPermissionUnknown -> {
            Dialog(
                onDismissRequest = { },
                DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                ) {
                    CircularProgressIndicator()
                }
            }
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
            shouldShowRational = shouldShowRationale.value,
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
                shouldShowRationale.value = it
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
    if (showProgress.value) {
        Dialog(
            onDismissRequest = { },
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                CircularProgressIndicator()
            }
        }

    }
    if (uiStateMainViewModel.isCheckedIn && showProgress.value) {
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                homeViewModel.intents.send(HomeIntent.CheckInSuccess)
            }
        }
        showProgress.value = false
    } else if (uiStateMainViewModel.isCheckedOut && showProgress.value) {
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                homeViewModel.intents.send(HomeIntent.CheckOutSuccess)
            }
        }
        showProgress.value = false
    } else if(uiStateMainViewModel.hideProgress){
        showProgress.value = false
    }
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            homeViewModel.intents.send(HomeIntent.Init(areLocationPermissionsGranted(context)))
        }
    }
}