/**
 * Created by developer on 04-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.core.model

sealed class Response<out T> {
    data object Loading : Response<Nothing>()
    data class Success<out T>(val data : T) : Response<T>()
    data class Failure(val e : Exception) : Response<Nothing>()
}

sealed interface LoadingState {
    data object Idle : LoadingState
    data object Loading : LoadingState
}