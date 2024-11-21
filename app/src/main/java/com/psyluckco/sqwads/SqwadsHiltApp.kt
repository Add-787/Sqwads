/**
 * Created by developer on 07-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class SqwadsHiltApp : Application() {

    override fun onCreate() {
        super.onCreate()
//        if(BuildConfig.DEBUG) {
//            Timber.plant(Timber.DebugTree())
//        }
    }
}