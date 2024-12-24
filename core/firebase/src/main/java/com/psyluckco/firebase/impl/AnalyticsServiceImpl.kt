/**
 * Created by developer on 23-12-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.firebase.impl

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import com.psyluckco.firebase.AnalyticsService
import javax.inject.Inject

class AnalyticsServiceImpl @Inject constructor(
    private val analytics: FirebaseAnalytics
): AnalyticsService {
    override fun logLoginEvent() {
        analytics.logEvent(FirebaseAnalytics.Event.LOGIN, Bundle())
    }

}