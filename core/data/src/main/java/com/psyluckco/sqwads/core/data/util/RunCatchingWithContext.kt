/**
 * Created by developer on 05-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.core.data.util

import com.psyluckco.sqwads.core.model.di.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

suspend fun <T> runCatchingWithContext(
    dispatcher: CoroutineDispatcher,
    block: suspend CoroutineScope.() -> T
): Result<T> = withContext(dispatcher) {
    runCatching {
        block()
    }
}