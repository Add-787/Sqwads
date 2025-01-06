/**
 * Created by developer on 01-01-2025.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.feature.stats

import com.psyluckco.sqwads.core.common.BaseViewModel
import com.psyluckco.sqwads.core.common.LogService
import com.psyluckco.sqwads.core.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    logService: LogService
): BaseViewModel(logService) {


}