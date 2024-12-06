/**
 * Created by developer on 06-12-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.feature.joined_room

import com.psyluckco.sqwads.core.common.BaseViewModel
import com.psyluckco.sqwads.core.common.LogService
import javax.inject.Inject

class JoinedRoomViewModel @Inject constructor(
    logService: LogService
) : BaseViewModel(logService) {

    override fun onCleared() {
        super.onCleared()

    }
}