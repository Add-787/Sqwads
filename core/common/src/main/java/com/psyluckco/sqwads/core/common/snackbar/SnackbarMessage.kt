/**
 * Created by developer on 05-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.core.common.snackbar

import android.content.res.Resources
import androidx.annotation.StringRes

sealed class SnackbarMessage {
    class StringSnackbar(val message : String) : SnackbarMessage()
    class ResourceSnackbar(@StringRes val message : Int) : SnackbarMessage()

    companion object {
        fun SnackbarMessage.toMessage(resources: Resources) : String {
            return when(this) {
                is StringSnackbar -> this.message
                is ResourceSnackbar -> resources.getString(this.message)
            }
        }
    }
}