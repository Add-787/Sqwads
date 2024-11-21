package com.psyluckco.sqwads.core.common

interface LogService {
    fun logNonFatalCrash(throwable : Throwable)
}