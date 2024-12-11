/**
 * Created by developer on 04-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.core.common.impl

import com.psyluckco.sqwads.core.common.LogService
import timber.log.Timber
import javax.inject.Inject

class LogServiceImpl @Inject constructor() : LogService {
    override fun logNonFatalCrash(throwable: Throwable) {
        throwable.message.let { message -> Timber.log(1,message) }
    }
}