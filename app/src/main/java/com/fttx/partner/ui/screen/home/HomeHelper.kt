package com.fttx.partner.ui.screen.home

import com.fttx.partner.domain.model.Customer
import com.fttx.partner.domain.model.Ticket
import com.fttx.partner.domain.model.User
import com.fttx.partner.ui.mvicore.IEffect
import com.fttx.partner.ui.mvicore.IIntent
import com.fttx.partner.ui.mvicore.IState

sealed class HomeIntent : IIntent {
    data class Init(val isLocationPermissionGranted: Boolean) : HomeIntent()
    data object BackCta : HomeIntent()
    data class TicketCardCta(val ticket: Ticket) : HomeIntent()
    data class PhoneCta(val ticket: Ticket) : HomeIntent()
    data object AddCta : HomeIntent()
    data object AccountCta : HomeIntent()
    data object RequestLocationPermission : HomeIntent()
    data object LocationPermissionGranted : HomeIntent()
    data object LocationPermissionDenied : HomeIntent()
    data object LocationPermissionRevoked : HomeIntent()
    data object CheckIn : HomeIntent()
    data object CheckInSuccess : HomeIntent()
    data object CheckOut : HomeIntent()
    data object CheckOutSuccess : HomeIntent()
    data object EmptyError : HomeIntent()
}

sealed class HomeEffect : IEffect {
    data object NavigateBack : HomeEffect()
    data class NavigateToTicketDetails(val ticket: Ticket) : HomeEffect()
    data class NavigateToAddTicket(val customer: Customer) : HomeEffect()
    data class NavigateToAccount(val user: User) : HomeEffect()
    data class NavigateToCall(val ticket: Ticket) : HomeEffect()
    data object NavigateToLocationPermissionRequiredPopUp : HomeEffect()
    data object NavigateToLocationPermissionRequiredSettingsPopUp : HomeEffect()
    data object ShowProgress : HomeEffect()
    data object AutoCheckout : HomeEffect()
}

data class HomeState(
    val id: Int = -1,
    val locationPermissionState: LocationPermissionState = LocationPermissionState.LocationPermissionUnknown,
    val user: User = User(-1, "", "", "", false,false,false),
    val tickets: List<Ticket> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val isCheckedIn: Boolean = false,
    val isCheckedOut: Boolean = false,
) : IState

enum class LocationPermissionState() {
    LocationPermissionGranted,
    LocationPermissionDenied,
    LocationPermissionRevoked,
    LocationPermissionUnknown
}