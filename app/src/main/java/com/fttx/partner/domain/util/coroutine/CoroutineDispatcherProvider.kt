package com.fttx.partner.domain.util.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

val scopedJob = CoroutineScope(SupervisorJob() + Dispatchers.Main)

interface CoroutineDispatcherProvider {
    val dispatcherDefault: CoroutineDispatcher
    val dispatcherIO: CoroutineDispatcher

    suspend fun <T> switchToDefault(block: suspend CoroutineScope.() -> T): T

    suspend fun <T> switchToIO(block: suspend CoroutineScope.() -> T): T
}
