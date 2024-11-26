/**
 * Created by developer on 26-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.core.design

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector

sealed class IconType {
    data class Vector(val imageVector: ImageVector) : IconType()
    data class Bitmap(@DrawableRes val painterId : Int) : IconType()
}