package com.fttx.partner.ui.screen.calender

import androidx.lifecycle.ViewModel
import com.fttx.partner.domain.usecase.GetTicketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val getTicketUseCase: GetTicketUseCase
) : ViewModel(){

    init {

    }
}