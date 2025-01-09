package com.fttx.partner.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fttx.partner.data.network.util.SemaaiResult
import com.fttx.partner.data.source.local.datastore.DataStorePreferences
import com.fttx.partner.domain.usecase.ticket.GetTicketUseCase
import com.fttx.partner.ui.mock.getCustomer
import com.fttx.partner.ui.mvicore.IModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTicketUseCase: GetTicketUseCase,
    private val dataStorePreferences: DataStorePreferences,
) : ViewModel(), IModel<HomeState, HomeIntent, HomeEffect> {

    override val intents: Channel<HomeIntent> = Channel(Channel.UNLIMITED)

    private val _uiState = MutableStateFlow(HomeState())
    override val uiState: StateFlow<HomeState>
        get() = _uiState.asStateFlow()

    private val _uiEffect = Channel<HomeEffect>()
    override val uiEffect: Flow<HomeEffect>
        get() = _uiEffect.receiveAsFlow()

    init {
        handleIntent()
    }

    override fun handleIntent() {
        viewModelScope.launch {
            intents.receiveAsFlow().collect {
                when (it) {
                    is HomeIntent.Init -> {
                        getTickets(it.isLocationPermissionGranted)
                    }

                    HomeIntent.AddCta -> {
                        _uiEffect.send(HomeEffect.NavigateToAddTicket(getCustomer()))
                    }

                    HomeIntent.BackCta -> {
                        _uiEffect.send(HomeEffect.NavigateBack)
                    }

                    is HomeIntent.TicketCardCta -> {
                        if (dataStorePreferences.isUserCheckedIn()) {
                            _uiEffect.send(HomeEffect.NavigateToTicketDetails(it.ticket))
                        } else {
                            _uiState.value = _uiState.value.copy(error = "Please Check In")
                        }
                    }

                    HomeIntent.AccountCta -> {
                        _uiEffect.send(HomeEffect.NavigateToAccount(uiState.value.user))
                    }

                    is HomeIntent.PhoneCta -> {
                        _uiEffect.send(HomeEffect.NavigateToCall(it.ticket))
                    }

                    is HomeIntent.RequestLocationPermission -> {
                        _uiEffect.send(HomeEffect.NavigateToLocationPermissionRequiredPopUp)
                    }

                    is HomeIntent.LocationPermissionGranted -> {
                        _uiState.value =
                            _uiState.value.copy(locationPermissionState = LocationPermissionState.LocationPermissionGranted)
                    }

                    is HomeIntent.LocationPermissionDenied -> {
                        _uiState.value =
                            _uiState.value.copy(locationPermissionState = LocationPermissionState.LocationPermissionDenied)
                    }

                    is HomeIntent.LocationPermissionRevoked -> {
                        _uiState.value =
                            _uiState.value.copy(locationPermissionState = LocationPermissionState.LocationPermissionRevoked)
                        _uiEffect.send(HomeEffect.NavigateToLocationPermissionRequiredSettingsPopUp)
                    }

                    HomeIntent.CheckIn -> {
                        _uiState.value = _uiState.value.copy(isCheckedIn = true, error = "")
                    }

                    HomeIntent.CheckOut -> {
                        _uiState.value = _uiState.value.copy(isCheckedIn = false, error = "")
                    }

                    HomeIntent.EmptyError -> {
                        _uiState.value = _uiState.value.copy(error = "")
                    }
                }
            }
        }
    }

    private suspend fun getTickets(locationPermissionGranted: Boolean) {
        dataStorePreferences.getUserId()?.let { userId ->
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = getTicketUseCase(userId)) {
                is SemaaiResult.Error -> {
                    _uiState.value =
                        _uiState.value.copy(isLoading = false, error = "Something Went Wrong")
                }

                is SemaaiResult.Success -> {
                    val lastCheckedInTimeStamp = dataStorePreferences.getCheckedInTimeStamp()
                    val calendar = Calendar.getInstance()
                    calendar.add(Calendar.DAY_OF_YEAR, -1)

                    val yesterday = calendar.time
                    val sdf = SimpleDateFormat("yyyyMMdd", Locale.getDefault())

                    if (sdf.format(Date(lastCheckedInTimeStamp)) == sdf.format(yesterday)) {
                        dataStorePreferences.saveUserCheckedIn(false)
                    }
                    _uiState.value =
                        _uiState.value.copy(
                            tickets = result.data.tickets,
                            user = result.data.user,
                            isLoading = false,
                            isCheckedIn = dataStorePreferences.isUserCheckedIn(),
                            locationPermissionState = if (locationPermissionGranted) LocationPermissionState.LocationPermissionGranted else LocationPermissionState.LocationPermissionDenied,
                            error = ""
                        )
                }
            }
        }
    }
}