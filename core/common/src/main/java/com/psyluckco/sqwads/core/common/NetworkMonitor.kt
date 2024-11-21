package com.psyluckco.sqwads.core.common

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    val isOnline : Flow<Boolean>
}